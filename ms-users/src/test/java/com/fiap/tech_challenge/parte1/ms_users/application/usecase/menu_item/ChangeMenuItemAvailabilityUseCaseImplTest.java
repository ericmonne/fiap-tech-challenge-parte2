package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChangeMenuItemAvailabilityUseCaseImplTest {

    private MenuItemGateway menuItemGateway;
    private IMenuItemMapper menuItemMapper;
    private ChangeMenuItemAvailabilityUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        menuItemMapper = mock(IMenuItemMapper.class);
        useCase = new ChangeMenuItemAvailabilityUseCaseImpl(menuItemGateway, menuItemMapper);
    }

    @Test
    void execute_shouldUpdateAvailabilityAndReturnDTO_whenMenuItemExistsAndInputIsValid() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);
        menuItem.setAvailableOnlyOnSite(false);

        MenuItemResponseDTO expectedResponse = new MenuItemResponseDTO(
                id, "Pizza", "Delicious",
                new BigDecimal("15.00"), true, "pizza.jpg",
                restaurantId
        );

        when(menuItemGateway.findById(id)).thenReturn(Optional.of(menuItem));
        when(menuItemMapper.toResponseDTO(menuItem)).thenReturn(expectedResponse);

        // Act
        MenuItemResponseDTO result = useCase.execute(id, true);

        // Assert
        assertEquals(expectedResponse, result);
        assertTrue(menuItem.getAvailableOnlyOnSite());
        verify(menuItemGateway).update(menuItem);
        verify(menuItemMapper).toResponseDTO(menuItem);
    }

    @Test
    void execute_shouldThrowMenuItemNotFoundException_whenMenuItemDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(menuItemGateway.findById(id)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> useCase.execute(id, true));

        verify(menuItemGateway, never()).update(any());
        verify(menuItemMapper, never()).toResponseDTO(any());
    }

    @Test
    void execute_shouldThrowIllegalArgumentException_whenAvailabilityIsNull() {
        UUID id = UUID.randomUUID();
        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);

        when(menuItemGateway.findById(id)).thenReturn(Optional.of(menuItem));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(id, null));

        assertEquals("availableOnlyOnSite cannot be null", exception.getMessage());

        verify(menuItemGateway, never()).update(any());
        verify(menuItemMapper, never()).toResponseDTO(any());
    }
}
