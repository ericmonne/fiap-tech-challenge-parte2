package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.domain.exception.InvalidOpeningHourRangeException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OpeningTimeRangeValidatorTest {

    private OpeningTimeRangeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new OpeningTimeRangeValidator();
    }

    private OpeningHour createOpeningHour(LocalTime opening, LocalTime closing) {
        return new OpeningHour(
                UUID.randomUUID(),
                WeekDay.SEGUNDA,
                opening,
                closing,
                new Restaurant()
        );
    }

    @Test
    void shouldNotThrowExceptionWhenOpeningTimeIsBeforeClosingTime() {
        OpeningHour validHour = createOpeningHour(LocalTime.of(9, 0), LocalTime.of(17, 0));
        assertDoesNotThrow(() -> validator.validate(validHour));
    }

    @Test
    void shouldThrowExceptionWhenOpeningTimeEqualsClosingTime() {
        OpeningHour invalidHour = createOpeningHour(LocalTime.of(9, 0), LocalTime.of(9, 0));
        InvalidOpeningHourRangeException exception = assertThrows(
                InvalidOpeningHourRangeException.class,
                () -> validator.validate(invalidHour)
        );
        assertTrue(exception.getMessage().contains("Horário inválido"));
    }

    @Test
    void shouldThrowExceptionWhenOpeningTimeIsAfterClosingTime() {
        OpeningHour invalidHour = createOpeningHour(LocalTime.of(18, 0), LocalTime.of(10, 0));
        InvalidOpeningHourRangeException exception = assertThrows(
                InvalidOpeningHourRangeException.class,
                () -> validator.validate(invalidHour)
        );
        assertTrue(exception.getMessage().contains("Horário inválido"));
    }
}
