package com.fiap.tech_challenge.parte1.ms_users.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO for updating menu item data.
 * All fields are optional - only provided fields will be updated.
 */
public record UpdateMenuItemDTO(
    @Size(min = 3, max = 100, message = "The name must have between 3 and 100 characters")
    String name,
    
    @Size(max = 500, message = "The description must have at most 500 characters")
    String description,
    
    @DecimalMin(value = "0.01", message = "The price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "The price must have at most 2 decimal places")
    BigDecimal price,
    
    Boolean availableOnlyOnSite,
    
    @Pattern(regexp = ".*\\.(jpg|jpeg|png|gif)$", message = "The image must be a JPG, JPEG, PNG, or GIF file")
    String imagePath
) {
    
    /**
     * Checks if all fields are null.
     * Useful for validation to ensure at least one field is being updated.
     *
     * @return true if all fields are null, false otherwise
     */
    public boolean allFieldsNull() {
        return name == null && 
               description == null && 
               price == null && 
               availableOnlyOnSite == null && 
               imagePath == null;
    }
}
