package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JdbcOpeningHourEntityTest {

    @Test
    void shouldCreateAndAccessJdbcOpeningHourEntity() {
        UUID id = UUID.randomUUID();
        WeekDay weekDay = WeekDay.SEGUNDA;
        LocalTime openingTime = LocalTime.of(8, 0);
        LocalTime closingTime = LocalTime.of(18, 0);
        UUID restaurantId = UUID.randomUUID();

        JdbcOpeningHourEntity entity = new JdbcOpeningHourEntity();
        entity.setId(id);
        entity.setWeekDay(weekDay);
        entity.setOpeningTime(openingTime);
        entity.setClosingTime(closingTime);
        entity.setRestaurantId(restaurantId);

        assertAll(
                () -> assertEquals(id, entity.getId(), "ID should match"),
                () -> assertEquals(weekDay, entity.getWeekDay(), "WeekDay should match"),
                () -> assertEquals(openingTime, entity.getOpeningTime(), "OpeningTime should match"),
                () -> assertEquals(closingTime, entity.getClosingTime(), "ClosingTime should match"),
                () -> assertEquals(restaurantId, entity.getRestaurantId(), "RestaurantId should match")
        );
    }

    @Test
    void shouldHandleNullValues() {
        JdbcOpeningHourEntity entity = new JdbcOpeningHourEntity();

        assertAll(
                () -> assertNull(entity.getId(), "ID should be null"),
                () -> assertNull(entity.getWeekDay(), "WeekDay should be null"),
                () -> assertNull(entity.getOpeningTime(), "OpeningTime should be null"),
                () -> assertNull(entity.getClosingTime(), "ClosingTime should be null"),
                () -> assertNull(entity.getRestaurantId(), "RestaurantId should be null")
        );
    }

    @Test
    void shouldUpdateValues() {
        JdbcOpeningHourEntity entity = new JdbcOpeningHourEntity();
        UUID initialId = UUID.randomUUID();
        UUID updatedId = UUID.randomUUID();

        entity.setId(initialId);
        entity.setWeekDay(WeekDay.SEGUNDA);
        entity.setOpeningTime(LocalTime.of(9, 0));

        entity.setId(updatedId);
        entity.setWeekDay(WeekDay.TERCA);
        entity.setOpeningTime(LocalTime.of(10, 0));

        assertAll(
                () -> assertEquals(updatedId, entity.getId(), "Updated ID should match"),
                () -> assertEquals(WeekDay.TERCA, entity.getWeekDay(), "Updated WeekDay should match"),
                () -> assertEquals(LocalTime.of(10, 0), entity.getOpeningTime(), "Updated OpeningTime should match")
        );
    }

    @Test
    void shouldHandleEdgeCasesForTimes() {
        JdbcOpeningHourEntity entity = new JdbcOpeningHourEntity();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime maxTime = LocalTime.MAX;

        entity.setOpeningTime(midnight);
        entity.setClosingTime(maxTime);

        assertAll(
                () -> assertEquals(midnight, entity.getOpeningTime(), "Midnight opening time should match"),
                () -> assertEquals(maxTime, entity.getClosingTime(), "Max closing time should match")
        );
    }
}