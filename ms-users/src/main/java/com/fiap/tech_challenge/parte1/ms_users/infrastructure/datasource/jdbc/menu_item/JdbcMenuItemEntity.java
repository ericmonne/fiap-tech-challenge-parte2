package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item;

import java.math.BigDecimal;
import java.util.UUID;

public class JdbcMenuItemEntity {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyOnSite;
    private String imagePath;
    private UUID restaurantId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getAvailableOnlyOnSite() {
        return availableOnlyOnSite;
    }

    public void setAvailableOnlyOnSite(Boolean availableOnlyOnSite) {
        this.availableOnlyOnSite = availableOnlyOnSite;
    }

    public String getImagePath() {
        return imagePath;
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
}
