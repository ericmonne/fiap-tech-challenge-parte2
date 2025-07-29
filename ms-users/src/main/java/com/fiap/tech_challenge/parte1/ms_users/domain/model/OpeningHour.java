package com.fiap.tech_challenge.parte1.ms_users.domain.model;

import java.time.LocalTime;
import java.util.UUID;

public class OpeningHour {
    private UUID id;
    private WeekDay weekDay;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Restaurant restaurant;

    public OpeningHour(){}

    public OpeningHour(UUID id, WeekDay weekDay, LocalTime openingTime, LocalTime closingTime, Restaurant restaurant) {
        this.id = id;
        this.weekDay = weekDay;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.restaurant = restaurant;
    }

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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
