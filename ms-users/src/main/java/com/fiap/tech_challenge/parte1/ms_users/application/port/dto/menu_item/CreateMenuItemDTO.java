package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for receiving data for creating a menu item.
 * Includes validations to ensure the integrity of the received data.
 */
public record CreateMenuItemDTO(

        @NotBlank(message = "The item name is required")
        @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters")
        String name,

        @NotBlank(message = "The description is required")
        @Size(max = 500, message = "The description must be at most 500 characters")
        String description,

        @NotNull(message = "The price is required")
        @Positive(message = "The price must be greater than zero")
        @DecimalMin(value = "0.01", message = "The price must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "The price must have at most 2 decimal places")
        BigDecimal price,

        @NotNull(message = "The availability indication for on-site only is required")
        Boolean availableOnlyOnSite,

        @NotBlank(message = "The image path is required")
        @Pattern(regexp = ".*\\.(jpg|jpeg|png|gif)$", message = "The image must be a JPG, JPEG, PNG, or GIF file")
        String imagePath,

        @NotNull(message = "The restaurant ID is required")
        UUID restaurantId

) {
}
