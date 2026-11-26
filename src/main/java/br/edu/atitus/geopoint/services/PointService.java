package br.edu.atitus.geopoint.services;

import br.edu.atitus.geopoint.dtos.PointRequest;
import br.edu.atitus.geopoint.entities.PointEntity;
import br.edu.atitus.geopoint.repositories.PointRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class PointService {
    private final PointRepository repository;

    public PointService(PointRepository repository) {
        this.repository = repository;
    }

    private void validar(PointRequest pointRequest) {
        if (pointRequest == null)
            throw new RuntimeException("Objeto Nulo");
        if (pointRequest.name() == null || pointRequest.name().isBlank())
            throw new RuntimeException("Nome Inválido");
        if (pointRequest.name().trim().length() > 100)
            throw new RuntimeException("Nome deve ter no máximo 100 caracteres");
        if (pointRequest.description() != null && pointRequest.description().trim().length() > 255)
            throw new RuntimeException("Descrição deve ter no máximo 255 caracteres");
        if (pointRequest.latitude() == null
                || pointRequest.latitude().compareTo(BigDecimal.valueOf(-90)) < 0
                || pointRequest.latitude().compareTo(BigDecimal.valueOf(90)) > 0)
            throw new RuntimeException("Latitude invalida - Intervalo aceito: -90 a 90");
        if (pointRequest.longitude() == null
                || pointRequest.longitude().compareTo(BigDecimal.valueOf(-180)) < 0
                || pointRequest.longitude().compareTo(BigDecimal.valueOf(180)) > 0)
            throw new RuntimeException("Longitude invalida - Intervalo aceito: -180 a 180");


        }


    }

    private PointEntity montar(UUID id, PointRequest pointRequest) {
        PointEntity point = new PointEntity();
        point.setId(id);
        point.setName(pointRequest.name().trim());
        point.setDescription(pointRequest.description() = null ? null : pointRequest.description().trim());
        point.setLatitude(pointRequest.latitude());
        point.setLongitude(pointRequest.longitude());
        return point;

}
