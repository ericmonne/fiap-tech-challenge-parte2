package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.OpeningHourMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcOpeningHourDataSource implements OpeningHourDataSource {

    private final JdbcOpeningHourRepository jdbcOpeningHourRepository;
    private final OpeningHourMapper openingHourMapper;

    public JdbcOpeningHourDataSource(JdbcOpeningHourRepository jdbcOpeningHourRepository, OpeningHourMapper openingHourMapper) {
        this.jdbcOpeningHourRepository = jdbcOpeningHourRepository;
        this.openingHourMapper = openingHourMapper;
    }

    @Override
    public UUID createOpeningHour(OpeningHour openingHour) {
        JdbcOpeningHourEntity jdbcOpeningHourEntity = openingHourMapper.toJdbcOpeningHourEntity(openingHour);
        return jdbcOpeningHourRepository.save(jdbcOpeningHourEntity);
    }

    @Override
    public Optional<OpeningHour> findById(UUID openingHourId) {
        return jdbcOpeningHourRepository.findById(openingHourId);
    }

    @Override
    public void update(OpeningHour openingHour) {
        JdbcOpeningHourEntity jdbcOpeningHourEntity = openingHourMapper.toJdbcOpeningHourEntity(openingHour);
        jdbcOpeningHourRepository.update(jdbcOpeningHourEntity);
    }

    @Override
    public List<OpeningHour> findByRestaurantId(UUID restaurantId) {
        return jdbcOpeningHourRepository.findByRestaurantId(restaurantId);
    }
}
