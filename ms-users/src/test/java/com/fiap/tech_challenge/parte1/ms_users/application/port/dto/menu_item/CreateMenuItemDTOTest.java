package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateMenuItemDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWithValidData() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "Burger",
                "Delicious burger",
                new BigDecimal("29.90"),
                true,
                "image.jpg",
                UUID.randomUUID()
        );

        Set<ConstraintViolation<CreateMenuItemDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldFailWhenNameIsBlank() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "  ",
                "Valid description",
                new BigDecimal("29.90"),
                true,
                "image.png",
                UUID.randomUUID()
        );

        Set<ConstraintViolation<CreateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenNameIsTooShort() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "AB",
                "Valid description",
                new BigDecimal("29.90"),
                true,
                "image.png",
                UUID.randomUUID()
        );

        Set<ConstraintViolation<CreateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenDescriptionIsTooLong() {
        String longDescription = "a".repeat(501);
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "Burger",
                longDescription,
                new BigDecimal("29.90"),
                true,
                "image.png",
                UUID.randomUUID()
        );

        Set<ConstraintViolation<CreateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenPriceIsNegative() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "Burger",
                "Description",
                new BigDecimal("-1.00"),
                true,
                "image.png",
                UUID.randomUUID()
        );

        Set<ConstraintViolation<CreateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenPriceHasTooManyDecimals() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "Burger",
                "Description",
                new BigDecimal("10.123"),
                true,
                "image.png",
                UUID.randomUUID()
        );

        Set<ConstraintViolation<CreateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenAvailableOnlyOnSiteIsNull() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "Burger",
                "Description",
                new BigDecimal("10.00"),
                null,
                "image.png",
                UUID.randomUUID()
        );

        Set<ConstraintViolation<CreateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailWhenImagePathIsInvalid() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "Burger",
                "Description",
                new BigDecimal("10.00"),
                true,
                "image.txt", // extensão inválida
                UUID.randomUUID()
        );

        Set<ConstraintViolation<CreateMenuItemDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }


}