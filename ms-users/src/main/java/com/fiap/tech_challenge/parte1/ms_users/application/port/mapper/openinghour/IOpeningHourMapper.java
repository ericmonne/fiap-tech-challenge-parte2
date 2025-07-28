package com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;

import java.util.List;

public interface IOpeningHourMapper {

    OpeningHourResponseDTO toOpeningHourResponseDTO(OpeningHour openinhHour);

    List<OpeningHourResponseDTO> toOpeningHourResponseDTO(List<OpeningHour> openinhHours);

    List<OpeningHour> toEntity(List<OpeningHourRequestDTO> openinhHoursRequestDTO);

    //List<JdbcAddressEntity> toJdbcAddressEntity(List<Address> addresses);
}
