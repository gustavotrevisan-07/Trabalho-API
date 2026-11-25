package br.edu.atitus.geopoint.repositories;

import br.edu.atitus.geopoint.entities.PointEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.UUID;

public class PointRepository {
    // declarando uma dependencia da classe
    private final JdbcTemplate jdbcTemplate;


    private final RowMapper<PointEntity> rowMapper = (rs, rowNum) -> {
        PointEntity point = new PointEntity();

        point.setId(rs.getObject("id", UUID.class));
        point.setName(rs.getString("name"));
        point.setDescription(rs.getString("description"));
        point.setLatitude(rs.getBigDecimal("latitude"));
        point.setLongitude(rs.getBigDecimal("longitude"));

        return point;
    };



    //injeçõo de dependencia via Construtor
    public PointRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public PointEntity insert(PointEntity point) {

        String sql = """
        INSERT INTO points (
            id, name, description, latitude, longitude
        )
        VALUES (?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(
                sql,
                point.getId(),
                point.getName(),
                point.getDescription(),
                point.getLatitude(),
                point.getLongitude()
        );

        return point;
    }

    public List<PointEntity> findAll() {

        String sql = """
        SELECT id, name, description, latitude, longitude
        FROM points
        ORDER BY name
        """;

        return jdbcTemplate.query(sql, rowMapper);
    }



}
