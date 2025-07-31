package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniqueMenuItemNameValidatorTest {

    private MenuItemGateway menuItemGateway;
    private UniqueMenuItemNameValidator validator;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        validator = new UniqueMenuItemNameValidator(menuItemGateway);
    }

    @Test
    void validate_shouldThrowException_whenMenuItemWithSameNameButDifferentIdExists() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        MenuItem menuItemToValidate = new MenuItem(id1, "Pizza", "yummy", new BigDecimal(15.0), true, "pizza.jpg", UUID.randomUUID());
        MenuItem existingMenuItem = new MenuItem(id2, "Pizza", "yummy", new BigDecimal(15.0), true, "pizza.jpg", UUID.randomUUID());

        when(menuItemGateway.findByName("Pizza")).thenReturn(Optional.of(existingMenuItem));

        MenuItemAlreadyExistsException exception = assertThrows(MenuItemAlreadyExistsException.class,
                () -> validator.validate(menuItemToValidate));

        assertTrue(exception.getMessage().contains("Pizza"));
        verify(menuItemGateway).findByName("Pizza");
    }

    @Test
    void validate_shouldNotThrowException_whenMenuItemWithSameNameAndSameIdExists() {
        UUID id = UUID.randomUUID();

        MenuItem menuItemToValidate = new MenuItem(id, "Pizza", "yummy", new BigDecimal(15.0), true, "pizza.jpg", UUID.randomUUID());
        MenuItem existingMenuItem = new MenuItem(id, "Pizza", "yummy", new BigDecimal(15.0), true, "pizza.jpg", UUID.randomUUID());


        when(menuItemGateway.findByName("Pizza")).thenReturn(Optional.of(existingMenuItem));

        assertDoesNotThrow(() -> validator.validate(menuItemToValidate));

        verify(menuItemGateway).findByName("Pizza");
    }

    @Test
    void validate_shouldNotThrowException_whenNoMenuItemWithSameNameExists() {
        MenuItem menuItemToValidate = new MenuItem(UUID.randomUUID(), "Pizza", "yummy", new BigDecimal(15.0), true, "pizza.jpg", UUID.randomUUID());

        when(menuItemGateway.findByName("Pizza")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.validate(menuItemToValidate));

        verify(menuItemGateway).findByName("Pizza");
    }
}
