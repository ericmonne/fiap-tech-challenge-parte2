package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.openinghour.IOpeningHourMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour.JdbcOpeningHourEntity;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpeningHourMapper implements IOpeningHourMapper {

    @Override
    public OpeningHourResponseDTO toOpeningHourResponseDTO(OpeningHour openingHour) {
        return new OpeningHourResponseDTO(
                openingHour.getId(),
                openingHour.getWeekDay(),
                openingHour.getOpeningTime(),
                openingHour.getClosingTime()
        );
    }

    @Override
    public List<OpeningHourResponseDTO> toOpeningHourResponseDTO(List<OpeningHour> openingHours) {
        return openingHours.stream().map(this::toOpeningHourResponseDTO).toList();
    }

    @Override
    public OpeningHour toEntity(OpeningHourRequestDTO openingHoursRequestDTO) {
        OpeningHour openingHour = new OpeningHour();
        openingHour.setWeekDay(openingHoursRequestDTO.weekDay());
        openingHour.setOpeningTime(openingHoursRequestDTO.openingTime());
        openingHour.setClosingTime(openingHoursRequestDTO.closingTime());
        return openingHour;
    }

    @Override
    public List<OpeningHour> toEntity(List<OpeningHourRequestDTO> openingHoursRequestDTO) {
        return openingHoursRequestDTO.stream().map(this::toEntity).toList();
    }

    @Override
    public JdbcOpeningHourEntity toJdbcOpeningHourEntity(OpeningHour openingHours) {
        JdbcOpeningHourEntity jdbcOpeningHourEntity = new JdbcOpeningHourEntity();
        jdbcOpeningHourEntity.setId(openingHours.getId());
        jdbcOpeningHourEntity.setWeekDay(openingHours.getWeekDay());
        jdbcOpeningHourEntity.setOpeningTime(openingHours.getOpeningTime());
        jdbcOpeningHourEntity.setClosingTime(openingHours.getClosingTime());
        jdbcOpeningHourEntity.setRestaurantId(openingHours.getRestaurant().getId());
        return jdbcOpeningHourEntity;
    }

}
