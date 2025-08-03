package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.controller.MenuItemControllerInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class MenuItemControllerTest {

    private MenuItemControllerInputPort inputPort;
    private MenuItemController controller;

    @BeforeEach
    void setUp() {
        inputPort = mock(MenuItemControllerInputPort.class);
        controller = new MenuItemController(inputPort);
    }

    @Test
    void testCreateMenuItem() {
        CreateMenuItemDTO dto = new CreateMenuItemDTO("Pizza", "Delicious", BigDecimal.TEN, true, "image.jpg", UUID.randomUUID());
        UUID id = UUID.randomUUID();
        MenuItemResponseDTO response = new MenuItemResponseDTO(id, "Pizza", "Delicious", BigDecimal.TEN, true, "image.jpg", UUID.randomUUID());

        when(inputPort.create(dto)).thenReturn(response);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost");
        ResponseEntity<MenuItemResponseDTO> result = controller.createMenuItem(dto, builder);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getHeaders().getLocation()).isNotNull();
        assertThat(result.getHeaders().getLocation().toString()).hasToString("http://localhost/menu-items/" + id);
        assertDoesNotThrow(() -> URI.create(result.getHeaders().getLocation().toString()));
        assertDoesNotThrow(() -> URI.create("http://localhost/menu-items/" + id));
        assertDoesNotThrow(() -> URI.create("http://localhost"));
        assertThat(result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void testGetMenuItemById() {
        UUID id = UUID.randomUUID();
        MenuItemResponseDTO response = new MenuItemResponseDTO(id, "Pizza", "Delicious", BigDecimal.TEN, true, "image.jpg", UUID.randomUUID());

        when(inputPort.getById(id)).thenReturn(response);

        ResponseEntity<MenuItemResponseDTO> result = controller.getMenuItemById(id);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void testGetAllMenuItems() {
        List<MenuItemResponseDTO> list = List.of(
                new MenuItemResponseDTO(UUID.randomUUID(), "Pizza", "Delicious", BigDecimal.TEN, true, "image.jpg", UUID.randomUUID()),
                new MenuItemResponseDTO(UUID.randomUUID(), "Burger", "Yummy", BigDecimal.ONE, true, "image2.jpg", UUID.randomUUID())
        );

        when(inputPort.findAll()).thenReturn(list);

        ResponseEntity<List<MenuItemResponseDTO>> result = controller.getAllMenuItems();

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(list);
    }

    @Test
    void testGetPaginatedMenuItemsValid() {
        PaginatedResponseDTO<MenuItemResponseDTO> page = new PaginatedResponseDTO<>(List.of(), 0, 10, 1, 10);

        when(inputPort.findAllPaginated(10, 0)).thenReturn(page);

        ResponseEntity<PaginatedResponseDTO<MenuItemResponseDTO>> result = controller.getPaginatedMenuItems(10, 0);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(page);
    }

    @Test
    void testGetPaginatedMenuItemsInvalid() {
        ResponseEntity<PaginatedResponseDTO<MenuItemResponseDTO>> result = controller.getPaginatedMenuItems(-1, 0);
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNull();
        assertThat(result.getStatusCodeValue()).isEqualTo(400);

    }

    @Test
    void testUpdateMenuItem() {
        UUID menuItemId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO("Updated", "Yummy", BigDecimal.ONE, false, "image.jpg");
        MenuItemResponseDTO response = new MenuItemResponseDTO(menuItemId, "Updated", "Yummy", BigDecimal.ONE, false, "image.jpg", restaurantId);

        when(inputPort.update(menuItemId, dto)).thenReturn(response);

        ResponseEntity<MenuItemResponseDTO> result = controller.updateMenuItem(menuItemId, dto);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    void testDeleteMenuItem() {
        UUID id = UUID.randomUUID();

        assertDoesNotThrow(() -> controller.deleteMenuItem(id));
        verify(inputPort, times(1)).delete(id);
    }

    @Test
    void testChangeAvailability() {
        UUID id = UUID.randomUUID();
        Boolean available = true;
        MenuItemResponseDTO response = new MenuItemResponseDTO(id, "Name", "Desc", BigDecimal.TEN, true, "image.jpg", UUID.randomUUID());

        when(inputPort.changeAvailability(id, available)).thenReturn(response);

        ResponseEntity<MenuItemResponseDTO> result = controller.changeMenuItemAvailability(id, available);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo(response);
    }
}
