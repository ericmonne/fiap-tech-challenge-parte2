package com.fiap.tech_challenge.parte1.ms_users.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class MenuItemTest {

    @Test
    void shouldCreateMenuItemWithAllFields() {
        //Arrange
        UUID id = UUID.randomUUID();
        String name = "Hambúrguer";
        String description = "Delicioso hambúrguer artesanal";
        BigDecimal price = new BigDecimal("29.90");
        boolean availableOnlyOnSite = true;
        String imagePath = "path/to/image.jpg";
        UUID restaurantId = UUID.randomUUID();

        //Act
        MenuItem menuItem = new MenuItem(id, name, description, price, availableOnlyOnSite, imagePath, restaurantId);

        //Assert
        assertEquals(id, menuItem.getId());
        assertEquals(name, menuItem.getName());
        assertEquals(description, menuItem.getDescription());
        assertEquals(price, menuItem.getPrice());
        assertEquals(availableOnlyOnSite, menuItem.getAvailableOnlyOnSite());
        assertEquals(imagePath, menuItem.getImagePath());
        assertEquals(restaurantId, menuItem.getRestaurantId());
    }

    @Test
    void shouldCreateMenuItemWithRequiredFields() {
        // Arrange
        String name = "Hambúrguer";
        BigDecimal price = new BigDecimal("29.90");
        UUID restaurantId = UUID.randomUUID();

        // Act
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setRestaurantId(restaurantId);

        // Assert
        assertEquals(name, menuItem.getName());
        assertEquals(price, menuItem.getPrice());
        assertEquals(restaurantId, menuItem.getRestaurantId());
    }

    @Test
    void shouldCreateNewMenuItemWithUpdatedId() {
        UUID originalId = UUID.randomUUID();
        UUID newId = UUID.randomUUID();

        MenuItem original = new MenuItem(
                originalId,
                "Pizza",
                "Delicious cheese pizza",
                new BigDecimal("39.90"),
                false,
                "pizza.jpg",
                UUID.randomUUID()
        );

        MenuItem copy = original.withId(newId);

        assertNotSame(original, copy); // novo objeto
        assertEquals(newId, copy.getId());
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getDescription(), copy.getDescription());
        assertEquals(original.getPrice(), copy.getPrice());
        assertEquals(original.getAvailableOnlyOnSite(), copy.getAvailableOnlyOnSite());
        assertEquals(original.getImagePath(), copy.getImagePath());
        assertEquals(original.getRestaurantId(), copy.getRestaurantId());
    }


}