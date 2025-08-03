package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpeningHourRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWhenAllFieldsArePresent() {
        OpeningHourRequestDTO dto = new OpeningHourRequestDTO(
                WeekDay.SEGUNDA,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0)
        );

        Set<ConstraintViolation<OpeningHourRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Expected no constraint violations");
    }

    @Test
    void shouldFailValidationWhenWeekDayIsNull() {
        OpeningHourRequestDTO dto = new OpeningHourRequestDTO(
                null,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0)
        );

        Set<ConstraintViolation<OpeningHourRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Week day is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailValidationWhenOpeningTimeIsNull() {
        OpeningHourRequestDTO dto = new OpeningHourRequestDTO(
                WeekDay.SEGUNDA,
                null,
                LocalTime.of(18, 0)
        );

        Set<ConstraintViolation<OpeningHourRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Opening time is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailValidationWhenClosingTimeIsNull() {
        OpeningHourRequestDTO dto = new OpeningHourRequestDTO(
                WeekDay.SEGUNDA,
                LocalTime.of(9, 0),
                null
        );

        Set<ConstraintViolation<OpeningHourRequestDTO>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        assertEquals("Closing time is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailValidationWhenAllFieldsAreNull() {
        OpeningHourRequestDTO dto = new OpeningHourRequestDTO(null, null, null);

        Set<ConstraintViolation<OpeningHourRequestDTO>> violations = validator.validate(dto);

        assertEquals(3, violations.size());
    }
}
