package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemReadException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadAllMenuItemsUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private IMenuItemMapper menuItemMapper;

    @InjectMocks
    private ReadAllMenuItemsUseCaseImpl useCase;

    @Test
    void shouldReturnListOfMenuItemResponseDTOs_whenMenuItemsExist() {
        // Arrange
        MenuItem item1 = new MenuItem(UUID.randomUUID(), "Burger", "Tasty", new BigDecimal("10.00"), false, "/img1", UUID.randomUUID());
        MenuItem item2 = new MenuItem(UUID.randomUUID(), "Pizza", "Cheesy", new BigDecimal("15.00"), true, "/img2", UUID.randomUUID());
        List<MenuItem> domainItems = List.of(item1, item2);

        MenuItemResponseDTO dto1 = new MenuItemResponseDTO(item1.getId(), item1.getName(), item1.getDescription(), item1.getPrice(), item1.getAvailableOnlyOnSite(), item1.getImagePath(), item1.getRestaurantId());
        MenuItemResponseDTO dto2 = new MenuItemResponseDTO(item2.getId(), item2.getName(), item2.getDescription(), item2.getPrice(), item2.getAvailableOnlyOnSite(), item2.getImagePath(), item2.getRestaurantId());
        List<MenuItemResponseDTO> expectedDtos = List.of(dto1, dto2);

        when(menuItemGateway.findAll()).thenReturn(domainItems);
        when(menuItemMapper.mapList(domainItems)).thenReturn(expectedDtos);

        // Act
        List<MenuItemResponseDTO> result = useCase.execute();

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactly(dto1, dto2);

        verify(menuItemGateway, times(1)).findAll();
        verify(menuItemMapper, times(1)).mapList(domainItems);
    }

    @Test
    void shouldReturnEmptyList_whenNoMenuItemsExist() {
        // Arrange
        when(menuItemGateway.findAll()).thenReturn(List.of());
        when(menuItemMapper.mapList(List.of())).thenReturn(List.of());

        // Act
        List<MenuItemResponseDTO> result = useCase.execute();

        // Assert
        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(menuItemGateway).findAll();
        verify(menuItemMapper).mapList(List.of());
    }

    @Test
    void shouldThrowRuntimeException_whenGatewayThrowsException() {
        // Arrange
        RuntimeException unexpectedException = new RuntimeException("Database failure");
        when(menuItemGateway.findAll()).thenThrow(unexpectedException);

        // Act & Assert
        RuntimeException thrown = Assertions.catchThrowableOfType(() -> useCase.execute(), RuntimeException.class);

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Database failure");

        verify(menuItemGateway).findAll();
        verify(menuItemMapper, never()).mapList(any());
    }

    @Test
    void shouldRethrowMenuItemReadException_whenUnexpectedErrorOccurs() {
        // Arrange
        RuntimeException cause = new IllegalStateException("Unexpected system error");
        when(menuItemGateway.findAll()).thenThrow(cause);

        // Act & Assert
        MenuItemReadException thrown = assertThrows(MenuItemReadException.class, () -> useCase.execute());

        assertThat(thrown)
                .isInstanceOf(MenuItemReadException.class);
        assertThat(thrown.getMessage()).isEqualTo("Erro ao buscar itens do menu");
        assertThat(cause).isEqualTo(thrown.getCause());

        verify(menuItemGateway).findAll();
        verify(menuItemMapper, never()).mapList(any());
    }

}
