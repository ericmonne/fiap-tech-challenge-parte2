package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuItemsByRestaurantRequestDTOTest {

    @Test
    void shouldCreateDTOWhenValidParameters() {
        UUID restaurantId = UUID.randomUUID();
        int size = 20;
        int offset = 0;

        MenuItemsByRestaurantRequestDTO dto = new MenuItemsByRestaurantRequestDTO(restaurantId, size, offset);

        assertEquals(restaurantId, dto.restaurantId());
        assertEquals(size, dto.size());
        assertEquals(offset, dto.offset());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantIdIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new MenuItemsByRestaurantRequestDTO(null, 10, 0));

        assertEquals("Restaurant ID cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZero() {
        UUID restaurantId = UUID.randomUUID();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new MenuItemsByRestaurantRequestDTO(restaurantId, 0, 0));

        assertEquals("Size must be between 1 and 50", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSizeIsGreaterThan50() {
        UUID restaurantId = UUID.randomUUID();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new MenuItemsByRestaurantRequestDTO(restaurantId, 51, 0));

        assertEquals("Size must be between 1 and 50", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOffsetIsNegative() {
        UUID restaurantId = UUID.randomUUID();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new MenuItemsByRestaurantRequestDTO(restaurantId, 10, -1));

        assertEquals("Offset must be >= 0", exception.getMessage());
    }
}
