package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteMenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @InjectMocks
    private DeleteMenuItemUseCaseImpl useCase;

    @Test
    void execute_shouldDeleteMenuItem_whenIdExists() {
        UUID id = UUID.randomUUID();

        when(menuItemGateway.existsById(id)).thenReturn(true);

        // Act
        useCase.execute(id);

        // Assert
        verify(menuItemGateway).existsById(id);
        verify(menuItemGateway).deleteById(id);
    }

    @Test
    void execute_shouldThrowException_whenIdDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(menuItemGateway.existsById(id)).thenReturn(false);

        // Act & Assert
        MenuItemNotFoundException thrown = assertThrows(MenuItemNotFoundException.class,
                () -> useCase.execute(id));

        assertTrue(thrown.getMessage().contains(id.toString()));

        verify(menuItemGateway).existsById(id);
        verify(menuItemGateway, never()).deleteById(any());
    }

    @Test
    void execute_shouldThrowRuntimeException_whenUnexpectedErrorOccurs() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(menuItemGateway.existsById(id)).thenReturn(true);
        doThrow(new DataAccessException("Database error") {}).when(menuItemGateway).deleteById(id);

        // Act & Assert
        DataAccessException thrown = assertThrows(DataAccessException.class,
                () -> useCase.execute(id));

        assertThat(thrown).isInstanceOf(DataAccessException.class).hasMessage("Database error");
        assertThat(thrown.getSuppressed()).isEmpty();
        assertThat(thrown.getMessage()).isEqualTo("Database error");

        verify(menuItemGateway).existsById(id);
        verify(menuItemGateway).deleteById(id);
    }
}
