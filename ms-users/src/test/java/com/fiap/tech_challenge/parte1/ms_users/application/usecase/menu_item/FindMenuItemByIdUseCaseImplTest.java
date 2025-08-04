package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class FindMenuItemByIdUseCaseImplTest {

    private MenuItemGateway menuItemGateway;
    private IMenuItemMapper mapper;
    private FindMenuItemByIdUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        menuItemGateway = mock(MenuItemGateway.class);
        mapper = mock(IMenuItemMapper.class);
        useCase = new FindMenuItemByIdUseCaseImpl(menuItemGateway, mapper);
    }

    @Test
    void execute_shouldReturnMenuItemResponseDTO_whenMenuItemExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        MenuItem menuItem = mock(MenuItem.class);
        MenuItemResponseDTO expectedDTO = mock(MenuItemResponseDTO.class);

        when(menuItemGateway.findById(id)).thenReturn(Optional.of(menuItem));
        when(mapper.toResponseDTO(menuItem)).thenReturn(expectedDTO);

        // Act
        MenuItemResponseDTO result = useCase.execute(id);

        // Assert
        assertThat(result).isEqualTo(expectedDTO);
        verify(menuItemGateway).findById(id);
        verify(mapper).toResponseDTO(menuItem);
    }

    @Test
    void execute_shouldThrowMenuItemNotFoundException_whenMenuItemDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(menuItemGateway.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(MenuItemNotFoundException.class)
                .hasMessageContaining(id.toString());

        verify(menuItemGateway).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void execute_shouldPropagateRuntimeException_whenGatewayThrowsUnexpectedError() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(menuItemGateway.findById(id)).thenThrow(new RuntimeException("Unexpected gateway failure"));

        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unexpected gateway failure");

        verify(menuItemGateway).findById(id);
        verifyNoInteractions(mapper);
    }

    @Test
    void execute_shouldPropagateRuntimeException_whenMapperThrowsUnexpectedError() {
        // Arrange
        UUID id = UUID.randomUUID();
        MenuItem menuItem = mock(MenuItem.class);

        when(menuItemGateway.findById(id)).thenReturn(Optional.of(menuItem));
        when(mapper.toResponseDTO(menuItem)).thenThrow(new RuntimeException("Mapper exploded"));

        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Mapper exploded");

        verify(menuItemGateway).findById(id);
        verify(mapper).toResponseDTO(menuItem);
    }
}
