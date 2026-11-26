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

    private PointEntity montar(UUID id, PointRequest pointRequest) {
        PointEntity point = new PointEntity();
        point.setId(id);
        point.setName(pointRequest.name().trim());
        point.setDescription(pointRequest.description() == null ? null : pointRequest.description().trim());
        point.setLatitude(pointRequest.latitude());
        point.setLongitude(pointRequest.longitude());
        return point;
    }

    public PointEntity insert(PointRequest pointRequest) {
        validar(pointRequest);
        PointEntity point = montar(UUID.randomUUID(), pointRequest);


        if (repository.existsByName(point.getName()))
            throw new RuntimeException("Já existe um ponto cadastrado com o nome: " + point.getName());

        return repository.insert(point);
    }

    public List<PointEntity> findAll() {

        return repository.findAll();
    }

    public PointEntity findById(UUID id) {
        PointEntity point = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ponto não localizado com o ID: " + id));
        return point;
    }

    public PointEntity update(UUID id, PointRequest pointRequest) {
        findById(id);
        PointEntity point = montar(id, pointRequest);


        if (repository.existsByNameAndIdNot(point.getName(), id))
            throw new RuntimeException("Já existe um ponto cadastrado com o nome: " + point.getName());

        repository.update(point);
        return point;
    }

    public void delete(UUID id) {
        findById(id);
        repository.delete(id);
    }
}