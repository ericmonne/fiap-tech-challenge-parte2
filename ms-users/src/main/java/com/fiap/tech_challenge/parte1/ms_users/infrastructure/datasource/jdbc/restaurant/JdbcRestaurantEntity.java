package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;

import java.util.UUID;

public class JdbcRestaurantEntity {

    private UUID id;
    private String name;
    private CuisineType cuisineType;
    private UUID userId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
