package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemPersistenceException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JdbcMenuItemDataSourceTest {

    private JdbcMenuItemRepository jdbcMenuItemRepository;
    private IMenuItemMapper menuItemMapper;
    private JdbcMenuItemDataSource dataSource;

    @BeforeEach
    void setUp() {
        jdbcMenuItemRepository = mock(JdbcMenuItemRepository.class);
        menuItemMapper = mock(IMenuItemMapper.class);
        dataSource = new JdbcMenuItemDataSource(jdbcMenuItemRepository, menuItemMapper);
    }

    @Test
    void testFindAllPaginated() {
        PaginatedResult<MenuItem> expected = new PaginatedResult<>(List.of(), 0, 0); // totalItems e totalPages

        when(jdbcMenuItemRepository.findAllPaginated(10, 0)).thenReturn(expected);

        PaginatedResult<MenuItem> result = dataSource.findAllPaginated(10, 0);

        assertThat(result).isEqualTo(expected);
        verify(jdbcMenuItemRepository).findAllPaginated(10, 0);
    }


    @Test
    void testFindAll() {
        List<MenuItem> expected = List.of();
        when(jdbcMenuItemRepository.findAll()).thenReturn(expected);

        List<MenuItem> result = dataSource.findAll();

        assertThat(result).isEqualTo(expected);
        verify(jdbcMenuItemRepository).findAll();
    }

    @Test
    void testSaveSuccess() {
        // Arrange
        UUID id = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(id, "Pizza Margherita", "Delicious", BigDecimal.valueOf(25.0), true, "pizza.jpg", UUID.randomUUID());
        JdbcMenuItemEntity jdbcEntity = new JdbcMenuItemEntity();

        when(menuItemMapper.toJdbcMenuItemEntity(menuItem)).thenReturn(jdbcEntity);
        when(jdbcMenuItemRepository.save(jdbcEntity)).thenReturn(menuItem);

        // Act
        MenuItem result = dataSource.save(menuItem);

        // Assert
        assertThat(result).isEqualTo(menuItem);
        verify(menuItemMapper).toJdbcMenuItemEntity(menuItem);
        verify(jdbcMenuItemRepository).save(jdbcEntity);
    }

    @Test
    void testSaveThrowsExceptionWhenRepositoryFails() {
        // Arrange
        UUID id = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(id, "Pizza Calabresa", "Spicy", BigDecimal.valueOf(28.0), true, "calabresa.jpg", UUID.randomUUID());
        JdbcMenuItemEntity jdbcEntity = new JdbcMenuItemEntity();

        when(menuItemMapper.toJdbcMenuItemEntity(menuItem)).thenReturn(jdbcEntity);
        when(jdbcMenuItemRepository.save(jdbcEntity))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dataSource.save(menuItem);
        });

        assertThat(exception).hasMessageContaining("Database error");

        verify(menuItemMapper).toJdbcMenuItemEntity(menuItem);
        verify(jdbcMenuItemRepository).save(jdbcEntity);
    }


    @Test
    void testSaveThrowsMenuItemPersistenceException() {
        // Arrange
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        MenuItem menuItem = new MenuItem(id, "Pizza Calabresa", "Apimentada", BigDecimal.valueOf(30.0), true, "calabresa.jpg", restaurantId);
        JdbcMenuItemEntity entity = new JdbcMenuItemEntity();

        when(menuItemMapper.toJdbcMenuItemEntity(menuItem)).thenReturn(entity);
        when(jdbcMenuItemRepository.save(entity))
                .thenThrow(new MenuItemPersistenceException("Erro ao acessar banco"));

        // Act & Assert
        MenuItemPersistenceException exception = assertThrows(
                MenuItemPersistenceException.class,
                () -> dataSource.save(menuItem)
        );

        assertThat(exception)
                .isInstanceOf(MenuItemPersistenceException.class)
                .hasMessageContaining("Erro ao acessar banco");

        // Optionally verify interactions
        verify(menuItemMapper).toJdbcMenuItemEntity(menuItem);
        verify(jdbcMenuItemRepository).save(entity);
    }


    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Optional<MenuItem> expected = Optional.of(new MenuItem());
        when(jdbcMenuItemRepository.findById(id)).thenReturn(expected);

        Optional<MenuItem> result = dataSource.findById(id);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();

        dataSource.deleteById(id);

        verify(jdbcMenuItemRepository).deleteById(id);
    }

    @Test
    void testUpdate() {
        MenuItem domain = new MenuItem(UUID.randomUUID(), "Pizza Calabresa", "Apimentada", BigDecimal.valueOf(30.0), true, "calabresa.jpg", UUID.randomUUID());

        JdbcMenuItemEntity entity = new JdbcMenuItemEntity();
        when(menuItemMapper.toJdbcMenuItemEntity(domain)).thenReturn(entity);

        dataSource.update(domain);

        verify(jdbcMenuItemRepository).update(entity);
    }

    @Test
    void testExistsById() {
        UUID id = UUID.randomUUID();
        when(jdbcMenuItemRepository.existsById(id)).thenReturn(true);

        assertTrue(dataSource.existsById(id));
    }

    @Test
    void testFindByName() {
        String name = "Pizza";
        Optional<MenuItem> expected = Optional.of(new MenuItem());
        when(jdbcMenuItemRepository.findByName(name)).thenReturn(expected);

        Optional<MenuItem> result = dataSource.findByName(name);

        assertEquals(expected, result);
    }

    @Test
    void testFindByRestaurantId() {
        UUID restaurantId = UUID.randomUUID();
        int size = 10;
        int offset = 0;

        MenuItemsByRestaurantRequestDTO dto = new MenuItemsByRestaurantRequestDTO(restaurantId, size, offset);
        PaginatedResult<MenuItem> expected = new PaginatedResult<>(List.of(), 0, 0); // content, totalItems, totalPages

        when(jdbcMenuItemRepository.findByRestaurantId(dto)).thenReturn(expected);

        PaginatedResult<MenuItem> result = dataSource.findByRestaurantId(dto);

        assertEquals(expected, result);
    }

}
