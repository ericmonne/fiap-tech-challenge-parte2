package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateMenuItemDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void allFieldsNull_ShouldReturnTrue() {
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO(null, null, null, null, null);
        assertTrue(dto.allFieldsNull());
    }

    @Test
    void allFieldsNull_ShouldReturnFalse_WhenAnyFieldIsNotNull() {
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO("Burger", null, null, null, null);
        assertFalse(dto.allFieldsNull());
    }

    @Test
    void validDTO_ShouldHaveNoViolations() {
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO(
                "Burger",
                "Delicious burger",
                new BigDecimal("10.99"),
                true,
                "image.jpg"
        );

        Set<ConstraintViolation<UpdateMenuItemDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidName_ShouldFailValidation() {
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO("AB", null, null, null, null);

        Set<ConstraintViolation<UpdateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void invalidDescription_ShouldFailValidation() {
        String longDesc = "A".repeat(501);
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO(null, longDesc, null, null, null);

        Set<ConstraintViolation<UpdateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

    @Test
    void negativePrice_ShouldFailValidation() {
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO(null, null, new BigDecimal("-1.00"), null, null);

        Set<ConstraintViolation<UpdateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }

    @Test
    void priceWithTooManyDecimals_ShouldFailValidation() {
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO(null, null, new BigDecimal("10.123"), null, null);
        Set<ConstraintViolation<UpdateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void invalidImagePath_ShouldFailValidation() {
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO(null, null, null, null, "image.bmp");

        Set<ConstraintViolation<UpdateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("imagePath")));
    }
}
