package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class RestaurantRequestDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateValidRestaurantRequestDTO() {
        AddressRequestDTO address = new AddressRequestDTO(
                "12345-678",
                "Rua Teste",
                123,
                "Complemento",
                "Bairro",
                "Cidade",
                "SP"
        );

        OpeningHourRequestDTO openingHour = new OpeningHourRequestDTO(
                WeekDay.DOMINGO,
                LocalTime.of(13, 0),
                LocalTime.of(22, 0)
        );

        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "Restaurante Teste",
                address,
                List.of(openingHour),
                CuisineType.ITALIANA
        );

        var violations = validator.validate(dto);
        assertTrue(violations.isEmpty(),
                "Violations found: " + violations.stream()
                        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                        .collect(Collectors.joining(", ")));
    }

    @Test
    void shouldFailValidationWhenNameIsBlank() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "",
                mock(AddressRequestDTO.class),
                List.of(mock(OpeningHourRequestDTO.class)),
                CuisineType.ITALIANA
        );

        var violations = validator.validate(dto);
        Set<String> messages = getViolationMessages(violations);
        assertTrue(messages.contains("Restaurant field 'name' is required"));
    }

    @Test
    void shouldFailValidationWhenNameIsNull() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                null,
                mock(AddressRequestDTO.class),
                List.of(mock(OpeningHourRequestDTO.class)),
                CuisineType.ITALIANA
        );

        var violations = validator.validate(dto);
        Set<String> messages = getViolationMessages(violations);
        assertTrue(messages.contains("Restaurant field 'name' is required"));
    }

    @Test
    void shouldFailValidationWhenAddressIsNull() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "Restaurante Teste",
                null,
                List.of(mock(OpeningHourRequestDTO.class)),
                CuisineType.ITALIANA
        );

        var violations = validator.validate(dto);
        Set<String> messages = getViolationMessages(violations);
        assertTrue(messages.contains("Restaurant must have an Address"));
    }

    @Test
    void shouldFailValidationWhenOpeningHoursIsEmpty() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "Restaurante Teste",
                mock(AddressRequestDTO.class),
                Collections.emptyList(),
                CuisineType.ITALIANA
        );

        var violations = validator.validate(dto);
        Set<String> messages = getViolationMessages(violations);
        assertTrue(messages.contains("Restaurant must have at least one Opening Hour settled"));
    }

    @Test
    void shouldFailValidationWhenOpeningHoursIsNull() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "Restaurante Teste",
                mock(AddressRequestDTO.class),
                null,
                CuisineType.ITALIANA
        );

        var violations = validator.validate(dto);
        Set<String> messages = getViolationMessages(violations);
        assertTrue(messages.contains("Restaurant must have at least one Opening Hour settled"));
    }

    @Test
    void shouldFailValidationWhenCuisineTypeIsNull() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "Restaurante Teste",
                mock(AddressRequestDTO.class),
                List.of(mock(OpeningHourRequestDTO.class)),
                null
        );

        var violations = validator.validate(dto);
        Set<String> messages = getViolationMessages(violations);
        assertTrue(messages.contains("Restaurant must have a Cuisine Type"));
    }

    private Set<String> getViolationMessages(Set<ConstraintViolation<RestaurantRequestDTO>> violations) {
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }
}