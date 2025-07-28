package com.fiap.tech_challenge.parte1.ms_users.domain.model;

import java.util.List;
import java.util.UUID;

public class Restaurant {

    private UUID id;
    private String name;
    private Address address;
    private List<CuisineType> cousineType;
    private List<OpeningHours> openingHoursList;
    private User user;

    public Restaurant(){}

    public Restaurant(UUID id, String name, Address address, List<CuisineType> cousineType, List<OpeningHours> openingHoursList, User user) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cousineType = cousineType;
        this.openingHoursList = openingHoursList;
        this.user = user;
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<CuisineType> getCousineType() {
        return cousineType;
    }

    public void setCousineType(List<CuisineType> cousineType) {
        this.cousineType = cousineType;
    }

    public List<OpeningHours> getOpeningHoursList() {
        return openingHoursList;
    }

    public void setOpeningHoursList(List<OpeningHours> openingHoursList) {
        this.openingHoursList = openingHoursList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
