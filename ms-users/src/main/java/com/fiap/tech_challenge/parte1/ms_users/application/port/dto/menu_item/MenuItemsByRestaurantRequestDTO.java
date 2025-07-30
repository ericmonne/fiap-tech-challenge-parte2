package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item;

import java.util.UUID;
/**
 * DTO para solicitar os itens de menu de um restaurante.
 *
 * @param restaurantId Identificador do restaurante.
 * @param size         Tamanho da página.
 * @param offset       Offset da página.
 */
public record MenuItemsByRestaurantRequestDTO(UUID restaurantId, int size, int offset)
{
    public MenuItemsByRestaurantRequestDTO {
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }
        if (size <= 0 || size > 50) {
            throw new IllegalArgumentException("Size must be between 1 and 50");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must be >= 0");
        }
    }
}
