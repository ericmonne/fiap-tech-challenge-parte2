package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UpdateMenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private IMenuItemMapper mapper;

    @Mock
    private MenuItemValidator validator1;

    @Mock
    private MenuItemValidator validator2;

    @InjectMocks
    private UpdateMenuItemUseCaseImpl useCase;

    private UUID menuItemId;
    private UUID restaurantId;
    private MenuItem existingItem;
    private UpdateMenuItemDTO updateDTO;

    @BeforeEach
    void setUp() {
        menuItemId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();
        existingItem = new MenuItem(
                menuItemId, "Old Burger", "Old desc", new BigDecimal("20.00"),
                true, "/img/old", restaurantId
        );
        updateDTO = new UpdateMenuItemDTO(
                "New Burger", "New desc", new BigDecimal("25.00"),
                false, "/img/new"
        );
        useCase = new UpdateMenuItemUseCaseImpl(menuItemGateway, mapper, List.of(validator1, validator2));
    }

    @Test
    void execute_shouldUpdateItemAndReturnResponseDTO_whenValidInput() {
        // Arrange
        MenuItemResponseDTO expectedResponse = new MenuItemResponseDTO(
                menuItemId, updateDTO.name(), updateDTO.description(), updateDTO.price(),
                updateDTO.availableOnlyOnSite(), updateDTO.imagePath(), restaurantId
        );

        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existingItem));
        when(mapper.toResponseDTO(existingItem)).thenReturn(expectedResponse);

        // Act
        MenuItemResponseDTO response = useCase.execute(menuItemId, updateDTO);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);

        assertEquals(updateDTO.name(), existingItem.getName());
        assertEquals(updateDTO.description(), existingItem.getDescription());
        assertEquals(updateDTO.price(), existingItem.getPrice());
        assertEquals(updateDTO.availableOnlyOnSite(), existingItem.getAvailableOnlyOnSite());
        assertEquals(updateDTO.imagePath(), existingItem.getImagePath());

        verify(menuItemGateway).findById(menuItemId);
        verify(menuItemGateway).update(existingItem);
        verify(validator1).validate(any(MenuItem.class));
        verify(validator2).validate(any(MenuItem.class));
        verify(mapper).toResponseDTO(existingItem);
        verifyNoMoreInteractions(menuItemGateway, mapper, validator1, validator2);
    }


    @Test
    void execute_shouldThrowMenuItemNotFoundException_whenItemNotFound() {
        // Arrange
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(MenuItemNotFoundException.class, () -> useCase.execute(menuItemId, updateDTO));

        verify(menuItemGateway).findById(menuItemId);
        verify(menuItemGateway, never()).update(any());
        verify(mapper, never()).toResponseDTO(any());
    }

    @Test
    void execute_shouldPropagateValidatorException_whenValidationFails() {
        // Arrange
        when(menuItemGateway.findById(menuItemId)).thenReturn(Optional.of(existingItem));
        doThrow(new IllegalArgumentException("Invalid menu item")).when(validator1).validate(any(MenuItem.class));

        // Act + Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(menuItemId, updateDTO));

        assertEquals("Invalid menu item", exception.getMessage());

        verify(validator1).validate(any());
        verify(validator2, never()).validate(any());
        verify(menuItemGateway, never()).update(any());
    }

    @Test
    void execute_shouldWrapUnexpectedRuntimeException() {
        // Arrange
        when(menuItemGateway.findById(menuItemId)).thenThrow(new RuntimeException("Database down"));

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> useCase.execute(menuItemId, updateDTO));

        assertEquals("Database down", exception.getMessage());

        verify(menuItemGateway).findById(menuItemId);
        verifyNoMoreInteractions(menuItemGateway, mapper, validator1, validator2);
    }
}

