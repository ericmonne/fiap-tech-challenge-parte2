package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;

import java.time.LocalTime;
import java.util.UUID;

public record OpeningHourResponseDTO(
        UUID id,
        WeekDay weekDay,
        LocalTime openingTime,
        LocalTime closingTime
) {
}
