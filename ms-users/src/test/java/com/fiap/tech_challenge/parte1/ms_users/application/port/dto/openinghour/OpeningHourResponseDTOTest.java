package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpeningHourResponseDTOTest {

    @Test
    void shouldCreateDTOAndReturnAllFields() {
        UUID id = UUID.randomUUID();
        WeekDay weekDay = WeekDay.SEXTA;
        LocalTime opening = LocalTime.of(10, 0);
        LocalTime closing = LocalTime.of(22, 0);

        OpeningHourResponseDTO dto = new OpeningHourResponseDTO(id, weekDay, opening, closing);

        assertEquals(id, dto.id());
        assertEquals(weekDay, dto.weekDay());
        assertEquals(opening, dto.openingTime());
        assertEquals(closing, dto.closingTime());
    }
}
