package com.fiap.tech_challenge.parte1.ms_users.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OpeningHourTest {

    @Test
    void shouldCreateEmptyOpeningHour() {
        OpeningHour openingHour = new OpeningHour();

        assertThat(openingHour).isNotNull();
        assertThat(openingHour.getId()).isNull();
        assertThat(openingHour.getWeekDay()).isNull();
        assertThat(openingHour.getOpeningTime()).isNull();
        assertThat(openingHour.getClosingTime()).isNull();
        assertThat(openingHour.getRestaurant()).isNull();
    }

    @Test
    void shouldCreateOpeningHourWithAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        WeekDay weekDay = WeekDay.SEGUNDA;
        LocalTime openingTime = LocalTime.of(10, 0);
        LocalTime closingTime = LocalTime.of(22, 0);
        Restaurant restaurant = new Restaurant();

        OpeningHour openingHour = new OpeningHour(id, weekDay, openingTime, closingTime, restaurant);

        assertThat(openingHour.getId()).isEqualTo(id);
        assertThat(openingHour.getWeekDay()).isEqualTo(weekDay);
        assertThat(openingHour.getOpeningTime()).isEqualTo(openingTime);
        assertThat(openingHour.getClosingTime()).isEqualTo(closingTime);
        assertThat(openingHour.getRestaurant()).isEqualTo(restaurant);
    }

    @Test
    void shouldSetAndGetProperties() {
        OpeningHour openingHour = new OpeningHour();
        UUID id = UUID.randomUUID();
        WeekDay weekDay = WeekDay.TERCA;
        LocalTime openingTime = LocalTime.of(9, 30);
        LocalTime closingTime = LocalTime.of(23, 0);
        Restaurant restaurant = new Restaurant();

        openingHour.setId(id);
        openingHour.setWeekDay(weekDay);
        openingHour.setOpeningTime(openingTime);
        openingHour.setClosingTime(closingTime);
        openingHour.setRestaurant(restaurant);

        assertThat(openingHour.getId()).isEqualTo(id);
        assertThat(openingHour.getWeekDay()).isEqualTo(weekDay);
        assertThat(openingHour.getOpeningTime()).isEqualTo(openingTime);
        assertThat(openingHour.getClosingTime()).isEqualTo(closingTime);
        assertThat(openingHour.getRestaurant()).isEqualTo(restaurant);
    }

    @Test
    void shouldHandleNullValuesInConstructor() {
        OpeningHour openingHour = new OpeningHour(null, null, null, null, null);

        assertThat(openingHour.getId()).isNull();
        assertThat(openingHour.getWeekDay()).isNull();
        assertThat(openingHour.getOpeningTime()).isNull();
        assertThat(openingHour.getClosingTime()).isNull();
        assertThat(openingHour.getRestaurant()).isNull();
    }

    @Test
    void shouldHandleNullValuesInSetters() {
        OpeningHour openingHour = new OpeningHour();

        openingHour.setId(null);
        openingHour.setWeekDay(null);
        openingHour.setOpeningTime(null);
        openingHour.setClosingTime(null);
        openingHour.setRestaurant(null);

        assertThat(openingHour.getId()).isNull();
        assertThat(openingHour.getWeekDay()).isNull();
        assertThat(openingHour.getOpeningTime()).isNull();
        assertThat(openingHour.getClosingTime()).isNull();
        assertThat(openingHour.getRestaurant()).isNull();
    }

    @Test
    void shouldHandleValidTimeRanges() {
        LocalTime openingTime = LocalTime.of(8, 0);
        LocalTime closingTime = LocalTime.of(18, 0);

        OpeningHour openingHour = new OpeningHour();
        openingHour.setOpeningTime(openingTime);
        openingHour.setClosingTime(closingTime);

        assertThat(openingHour.getOpeningTime()).isBefore(openingHour.getClosingTime());
    }
}