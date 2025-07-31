package com.fiap.tech_challenge.parte1.ms_users.application.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MenuItemControllerInputPortImplTest {

    @InjectMocks
    private MenuItemControllerInputPortImpl controller;

    @Mock private CreateMenuItemUseCase createMenuItemUseCase;
    @Mock private FindMenuItemByIdUseCase findMenuItemByIdUseCase;
    @Mock private ReadPaginatedMenuItemsUseCase readPaginatedMenuItemsUseCase;
    @Mock private ReadAllMenuItemsUseCase readAllMenuItemsUseCase;
    @Mock private UpdateMenuItemUseCase updateMenuItemUseCase;
    @Mock private ChangeMenuItemAvailabilityUseCase changeMenuItemAvailabilityUseCase;
    @Mock private DeleteMenuItemUseCase deleteMenuItemUseCase;
    @Mock private FindMenuItemsByRestaurantIdUseCase findMenuItemsByRestaurantIdUseCase;

    private final UUID id = UUID.randomUUID();
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new MenuItemControllerInputPortImpl(
                createMenuItemUseCase,
                findMenuItemByIdUseCase,
                readPaginatedMenuItemsUseCase,
                readAllMenuItemsUseCase,
                updateMenuItemUseCase,
                changeMenuItemAvailabilityUseCase,
                deleteMenuItemUseCase,
                findMenuItemsByRestaurantIdUseCase
        );
    }

    @Test
    void shouldCreateMenuItem() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO("Pizza", "Cheesy", new BigDecimal("25.00"), true, "image.jpg", UUID.randomUUID());
        MenuItemResponseDTO expected = mock(MenuItemResponseDTO.class);

        when(createMenuItemUseCase.execute(dto)).thenReturn(expected);

        MenuItemResponseDTO result = controller.create(dto);

        assertThat(result).isEqualTo(expected);
        verify(createMenuItemUseCase).execute(dto);
    }

    @Test
    void shouldUpdateMenuItem() {

        UpdateMenuItemDTO dto = new UpdateMenuItemDTO("New Name", "Updated", new BigDecimal("30.00"), false, "image.jpg");
        MenuItemResponseDTO expected = mock(MenuItemResponseDTO.class);

        when(updateMenuItemUseCase.execute(id, dto)).thenReturn(expected);

        MenuItemResponseDTO result = controller.update(id, dto);

        assertThat(result).isEqualTo(expected);
        verify(updateMenuItemUseCase).execute(id, dto);
    }

    @Test
    void shouldGetMenuItemById() {
        UUID menuItemId = UUID.randomUUID();
        MenuItemResponseDTO expected = mock(MenuItemResponseDTO.class);

        when(findMenuItemByIdUseCase.execute(menuItemId)).thenReturn(expected);

        MenuItemResponseDTO result = controller.getById(menuItemId);

        assertThat(result).isEqualTo(expected);
        verify(findMenuItemByIdUseCase).execute(menuItemId);
    }

    @Test
    void shouldReturnPaginatedMenuItems() {
        int size = 10;
        int offset = 0;
        PaginatedResponseDTO<MenuItemResponseDTO> expected = mock(PaginatedResponseDTO.class);

        when(readPaginatedMenuItemsUseCase.execute(size, offset)).thenReturn(expected);

        PaginatedResponseDTO<MenuItemResponseDTO> result = controller.findAllPaginated(size, offset);

        assertThat(result).isEqualTo(expected);
        verify(readPaginatedMenuItemsUseCase).execute(size, offset);
    }

    @Test
    void shouldReturnAllMenuItems() {
        List<MenuItemResponseDTO> expected = List.of(mock(MenuItemResponseDTO.class));

        when(readAllMenuItemsUseCase.execute()).thenReturn(expected);

        List<MenuItemResponseDTO> result = controller.findAll();

        assertThat(result).isEqualTo(expected);
        verify(readAllMenuItemsUseCase).execute();
    }

    @Test
    void shouldDeleteMenuItem() {
        UUID menuItemId = UUID.randomUUID();

        controller.delete(menuItemId);

        verify(deleteMenuItemUseCase).execute(menuItemId);
    }

    @Test
    void shouldChangeAvailability() {
        UUID menuItemId = UUID.randomUUID();
        Boolean availableOnlyOnSite = true;
        MenuItemResponseDTO expected = mock(MenuItemResponseDTO.class);

        when(changeMenuItemAvailabilityUseCase.execute(menuItemId, availableOnlyOnSite)).thenReturn(expected);

        MenuItemResponseDTO result = controller.changeAvailability(menuItemId, availableOnlyOnSite);

        assertThat(result).isEqualTo(expected);
        verify(changeMenuItemAvailabilityUseCase).execute(menuItemId, availableOnlyOnSite);
    }

    @Test
    void shouldFindByRestaurantId() {
        MenuItemsByRestaurantRequestDTO request = new MenuItemsByRestaurantRequestDTO(UUID.randomUUID(), 10, 0);
        PaginatedResponseDTO<MenuItemResponseDTO> expected = mock(PaginatedResponseDTO.class);

        when(findMenuItemsByRestaurantIdUseCase.execute(request)).thenReturn(expected);

        PaginatedResponseDTO<MenuItemResponseDTO> result = controller.findByRestaurantId(request);

        assertThat(result).isEqualTo(expected);
        verify(findMenuItemsByRestaurantIdUseCase).execute(request);
    }

    @Test
    void testCreateThrowsException() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO("Test", "Desc", new BigDecimal("10.00"), true, "image.jpg", UUID.randomUUID());
        when(createMenuItemUseCase.execute(dto)).thenThrow(new RuntimeException("Error"));

        Exception exception = assertThrows(RuntimeException.class, () -> controller.create(dto));
        assertEquals("Error", exception.getMessage());
        verify(createMenuItemUseCase).execute(dto);
    }

    @Test
    void testDeleteThrowsException() {
        doThrow(new IllegalArgumentException("Invalid ID")).when(deleteMenuItemUseCase).execute(id);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.delete(id));
        assertEquals("Invalid ID", exception.getMessage());
        verify(deleteMenuItemUseCase).execute(id);
    }
}
