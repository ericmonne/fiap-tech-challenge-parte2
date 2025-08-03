package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WeekDayValidatorTest {

    private WeekDayValidator validator;

    @BeforeEach
    void setUp() {
        validator = new WeekDayValidator();
    }

    @Test
    void shouldNotThrowExceptionForValidWeekDay() {
        OpeningHour validOpeningHour = new OpeningHour(
                UUID.randomUUID(),
                WeekDay.SEGUNDA,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                new Restaurant()
        );

        assertDoesNotThrow(() -> validator.validate(validOpeningHour));
    }
}
