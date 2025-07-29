package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;

import java.time.LocalTime;
import java.util.UUID;

public class JdbcOpeningHourEntity {
    private UUID id;
    private WeekDay weekDay;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private UUID restaurantId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }
}
