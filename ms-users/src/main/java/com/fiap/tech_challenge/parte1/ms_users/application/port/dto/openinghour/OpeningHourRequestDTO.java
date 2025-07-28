package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record OpeningHourRequestDTO(
        @NotNull(message = "Week day is required")
        WeekDay weekDay,

        @NotNull(message = "Opening time is required")
        LocalTime openingTime,

        @NotNull(message = "Closing time is required")
        LocalTime closingTime
) {
}
