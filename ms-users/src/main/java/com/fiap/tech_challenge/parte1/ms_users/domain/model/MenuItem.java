package com.fiap.tech_challenge.parte1.ms_users.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuItem {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyOnSite;
    private String imagePath;
    private UUID restaurantId;


    public MenuItem() {
    }

    public MenuItem(UUID id, String name, String description, BigDecimal price, Boolean availableOnlyOnSite, String imagePath, UUID restaurantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnlyOnSite = availableOnlyOnSite;
        this.imagePath = imagePath;
        this.restaurantId = restaurantId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getAvailableOnlyOnSite() {
        return availableOnlyOnSite;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailableOnlyOnSite(Boolean availableOnlyOnSite) {
        this.availableOnlyOnSite = availableOnlyOnSite;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    //verificar necessidade de adicionar restaurantId
    public MenuItem withId(UUID id) {
        this.id = id;
        return new MenuItem(id, name, description, price, availableOnlyOnSite, imagePath, restaurantId);
    }
}
