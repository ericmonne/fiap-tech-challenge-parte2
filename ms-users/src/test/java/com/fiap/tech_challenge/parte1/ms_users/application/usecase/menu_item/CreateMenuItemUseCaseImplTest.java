package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateMenuItemUseCaseImplTest {

    private MenuItemGateway menuItemGateway;
    private IMenuItemMapper menuItemMapper;
    private MenuItemValidator validator1;
    private MenuItemValidator validator2;
    private CreateMenuItemUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        menuItemMapper = mock(IMenuItemMapper.class);
        validator1 = mock(MenuItemValidator.class);
        validator2 = mock(MenuItemValidator.class);

        useCase = new CreateMenuItemUseCaseImpl(
                menuItemGateway,
                menuItemMapper,
                List.of(validator1, validator2)
        );
    }

    @Test
    void execute_shouldMapValidateSaveAndReturnDTO() {
        // Arrange
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "Pizza",
                "Delicious cheese pizza",
                new BigDecimal("25.0"),
                false,
                "pizza.jpg",
                UUID.randomUUID()
        );

        MenuItem menuItem = new MenuItem();
        MenuItem savedItem = new MenuItem();
        MenuItemResponseDTO responseDTO = new MenuItemResponseDTO(
                UUID.randomUUID(),
                "Pizza",
                "Delicious cheese pizza",
                new BigDecimal("25.0"),
                false,
                "pizza.jpg",
                UUID.randomUUID()
        );

        when(menuItemMapper.toEntity(dto)).thenReturn(menuItem);
        when(menuItemGateway.save(menuItem)).thenReturn(savedItem);
        when(menuItemMapper.toResponseDTO(savedItem)).thenReturn(responseDTO);

        // Act
        MenuItemResponseDTO result = useCase.execute(dto);

        // Assert
        assertEquals(responseDTO, result);

        verify(menuItemMapper).toEntity(dto);
        verify(validator1).validate(menuItem);
        verify(validator2).validate(menuItem);
        verify(menuItemGateway).save(menuItem);
        verify(menuItemMapper).toResponseDTO(savedItem);
    }

    @Test
    void execute_shouldThrowException_whenValidatorFails() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "Pizza",
                "Delicious cheese pizza",
                new BigDecimal("25.0"),
                false,
                "pizza.jpg",
                UUID.randomUUID()
        );

        MenuItem menuItem = new MenuItem();
        menuItem.setName("Pizza");

        when(menuItemMapper.toEntity(dto)).thenReturn(menuItem);

        doThrow(new MenuItemAlreadyExistsException(menuItem))
                .when(validator1).validate(menuItem);

        // Act & Assert
        MenuItemAlreadyExistsException thrown = assertThrows(MenuItemAlreadyExistsException.class,
                () -> useCase.execute(dto));

        assertTrue(thrown.getMessage().contains("Pizza"));

        verify(menuItemMapper).toEntity(dto);
        verify(validator1).validate(menuItem);
        verify(validator2, never()).validate(any());
        verify(menuItemGateway, never()).save(any());
        verify(menuItemMapper, never()).toResponseDTO(any());
    }


}
