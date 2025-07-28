package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.openinghour.IOpeningHourMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpeningHourMapper implements IOpeningHourMapper {

    @Override
    public OpeningHourResponseDTO toOpeningHourResponseDTO(OpeningHour openinhHour) {
        return null;
    }

    @Override
    public List<OpeningHourResponseDTO> toOpeningHourResponseDTO(List<OpeningHour> openinhHours) {
        return List.of();
    }

    @Override
    public List<OpeningHour> toEntity(List<OpeningHourRequestDTO> openinhHoursRequestDTO) {
        return List.of();
    }
}
