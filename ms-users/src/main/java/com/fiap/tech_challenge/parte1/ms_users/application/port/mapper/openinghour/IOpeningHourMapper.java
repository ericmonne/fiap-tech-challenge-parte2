package com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour.JdbcOpeningHourEntity;

import java.util.List;

public interface IOpeningHourMapper {

    OpeningHourResponseDTO toOpeningHourResponseDTO(OpeningHour openingHour);

    List<OpeningHourResponseDTO> toOpeningHourResponseDTO(List<OpeningHour> openingHours);

    OpeningHour toEntity(OpeningHourRequestDTO openingHoursRequestDTO);

    List<OpeningHour> toEntity(List<OpeningHourRequestDTO> openingHoursRequestDTO);

    JdbcOpeningHourEntity toJdbcOpeningHourEntity (OpeningHour openingHours);
}
