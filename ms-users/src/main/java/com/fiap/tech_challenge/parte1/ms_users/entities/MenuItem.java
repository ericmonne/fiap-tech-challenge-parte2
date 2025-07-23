package com.fiap.tech_challenge.parte1.ms_users.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuItem {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyOnSite;
    private String imagePath;


    public MenuItem() {
    }

    public MenuItem(UUID id, String name, String description, BigDecimal price, Boolean availableOnlyOnSite, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnlyOnSite = availableOnlyOnSite;
        this.imagePath = imagePath;
    }

    /**
     * Returns the unique identifier of the menu item.
     *
     * @return the unique identifier of the menu item
     */
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
}
