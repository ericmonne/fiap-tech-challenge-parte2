package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MenuItemGatewayImplTest {

    private MenuItemDataSource menuItemDataSource;
    private MenuItemGatewayImpl menuItemGateway;

    @BeforeEach
    void setUp() {
        menuItemDataSource = mock(MenuItemDataSource.class);
        menuItemGateway = new MenuItemGatewayImpl(menuItemDataSource);
    }

    @Test
    void testSaveDelegatesToDataSource() {
        MenuItem item = new MenuItem(UUID.randomUUID(), "Pizza Calabresa", "Apimentada", BigDecimal.valueOf(30.0), true, "calabresa.jpg", UUID.randomUUID());

        when(menuItemDataSource.save(item)).thenReturn(item);

        MenuItem result = menuItemGateway.save(item);

        verify(menuItemDataSource).save(item);
        assertEquals(item, result);
    }

    @Test
    void testUpdateDelegatesToDataSource() {
        MenuItem item = new MenuItem(UUID.randomUUID(), "Pizza Calabresa", "Apimentada", BigDecimal.valueOf(30.0), true, "calabresa.jpg", UUID.randomUUID());


        menuItemGateway.update(item);

        verify(menuItemDataSource).update(item);
    }

    @Test
    void testDeleteByIdDelegatesToDataSource() {
        UUID id = UUID.randomUUID();

        menuItemGateway.deleteById(id);

        verify(menuItemDataSource).deleteById(id);
    }

    @Test
    void testFindByIdDelegatesToDataSource() {
        UUID id = UUID.randomUUID();
        MenuItem item = new MenuItem(id, "Pizza Calabresa", "Apimentada", BigDecimal.valueOf(30.0), true, "calabresa.jpg", UUID.randomUUID());

        when(menuItemDataSource.findById(id)).thenReturn(Optional.of(item));

        Optional<MenuItem> result = menuItemGateway.findById(id);

        verify(menuItemDataSource).findById(id);
        assertTrue(result.isPresent());
        assertEquals(item, result.get());
    }

    @Test
    void testFindPaginatedDelegatesToDataSource() {
        int size = 10;
        int offset = 0;
        PaginatedResult<MenuItem> paginated = mock(PaginatedResult.class);
        when(menuItemDataSource.findAllPaginated(size, offset)).thenReturn(paginated);

        PaginatedResult<MenuItem> result = menuItemGateway.findPaginated(size, offset);

        verify(menuItemDataSource).findAllPaginated(size, offset);
        assertEquals(paginated, result);
    }

    @Test
    void testFindAllDelegatesToDataSource() {
        MenuItem pizzaCalabresa = new MenuItem(UUID.randomUUID(), "Pizza Calabresa", "Apimentada", BigDecimal.valueOf(30.0), true, "calabresa.jpg", UUID.randomUUID());
        MenuItem pizzaMussarela = new MenuItem(UUID.randomUUID(), "Pizza Mussarela", "Saborosa", BigDecimal.valueOf(25.0), true, "mussarela.jpg", UUID.randomUUID());
        List<MenuItem> items = List.of(pizzaCalabresa, pizzaMussarela);
        when(menuItemDataSource.findAll()).thenReturn(items);

        List<MenuItem> result = menuItemGateway.findAll();

        verify(menuItemDataSource).findAll();
        assertThat(items).hasSize(2);
        assertThat(items.get(0)).isEqualTo(pizzaCalabresa);
        assertThat(items.get(1)).isEqualTo(pizzaMussarela);
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(pizzaCalabresa);
        assertThat(result.get(1)).isEqualTo(pizzaMussarela);
        assertThat(result).isEqualTo(items);

    }

    @Test
    void testExistsByIdDelegatesToDataSource() {
        UUID id = UUID.randomUUID();
        when(menuItemDataSource.existsById(id)).thenReturn(true);

        boolean exists = menuItemGateway.existsById(id);

        verify(menuItemDataSource).existsById(id);
        assertTrue(exists);
    }

    @Test
    void testFindByNameDelegatesToDataSource() {
        String name = "Pizza Calabresa";
        MenuItem item = new MenuItem(UUID.randomUUID(), "Pizza Calabresa", "Apimentada", BigDecimal.valueOf(30.0), true, "calabresa.jpg", UUID.randomUUID());

        when(menuItemDataSource.findByName(name)).thenReturn(Optional.of(item));

        Optional<MenuItem> result = menuItemGateway.findByName(name);

        verify(menuItemDataSource).findByName(name);
        assertTrue(result.isPresent());
        assertEquals(item, result.get());
    }

    @Test
    void testFindByRestaurantIdDelegatesToDataSource() {
        UUID restaurantId = UUID.randomUUID();
        int size = 10;
        int offset = 0;

        MenuItemsByRestaurantRequestDTO request = new MenuItemsByRestaurantRequestDTO(restaurantId, size, offset);
        PaginatedResult<MenuItem> paginated = mock(PaginatedResult.class);
        when(menuItemDataSource.findByRestaurantId(request)).thenReturn(paginated);

        PaginatedResult<MenuItem> result = menuItemGateway.findByRestaurantId(request);

        verify(menuItemDataSource).findByRestaurantId(request);
        assertEquals(paginated, result);
    }
}
