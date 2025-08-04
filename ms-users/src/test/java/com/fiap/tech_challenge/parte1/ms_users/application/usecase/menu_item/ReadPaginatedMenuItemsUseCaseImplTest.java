package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReadPaginatedMenuItemsUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private IMenuItemMapper menuItemMapper;

    @InjectMocks
    private ReadPaginatedMenuItemsUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPaginatedResponseDTO_whenMenuItemsExist() {
        // Arrange
        int size = 2;
        int offset = 0;

        MenuItem menuItem1 = new MenuItem(UUID.randomUUID(), "Burger", "Tasty", new BigDecimal("10.00"), false, "/img1", UUID.randomUUID());
        MenuItem menuItem2 = new MenuItem(UUID.randomUUID(), "Pizza", "Cheesy", new BigDecimal("15.00"), true, "/img2", UUID.randomUUID());

        List<MenuItem> content = List.of(menuItem1, menuItem2);
        int totalItems = 10;
        int totalPages = 5;

        PaginatedResult<MenuItem> paginatedResult = new PaginatedResult<>(content, totalItems, totalPages);

        when(menuItemGateway.findPaginated(size, offset)).thenReturn(paginatedResult);

        MenuItemResponseDTO dto1 = new MenuItemResponseDTO(menuItem1.getId(), menuItem1.getName(), menuItem1.getDescription(), menuItem1.getPrice(), menuItem1.getAvailableOnlyOnSite(), menuItem1.getImagePath(), menuItem1.getRestaurantId());
        MenuItemResponseDTO dto2 = new MenuItemResponseDTO(menuItem2.getId(), menuItem2.getName(), menuItem2.getDescription(), menuItem2.getPrice(), menuItem2.getAvailableOnlyOnSite(), menuItem2.getImagePath(), menuItem2.getRestaurantId());

        when(menuItemMapper.toResponseDTO(menuItem1)).thenReturn(dto1);
        when(menuItemMapper.toResponseDTO(menuItem2)).thenReturn(dto2);


        // Act
        PaginatedResponseDTO<MenuItemResponseDTO> response = useCase.execute(size, offset);
        int expectedCurrentPage = offset / size;
        assertEquals(expectedCurrentPage, response.currentPage());

        // Assert
        assertNotNull(response);
        assertEquals(2, response.content().size());
        assertEquals(totalItems, response.totalItems());
        assertEquals(totalPages, response.totalPages());
        assertEquals(expectedCurrentPage, response.currentPage());
        assertEquals(size, response.pageSize());
        assertEquals(List.of(dto1, dto2), response.content());

        assertTrue(response.content().contains(dto1));
        assertTrue(response.content().contains(dto2));

        verify(menuItemGateway).findPaginated(size, offset);
        verify(menuItemMapper).toResponseDTO(menuItem1);
        verify(menuItemMapper).toResponseDTO(menuItem2);
    }

    @Test
    void execute_shouldReturnEmptyList_whenNoMenuItemsFound() {
        // Arrange
        int size = 5;
        int offset = 10;

        List<MenuItem> content = List.of();
        int totalItems = 0;
        int totalPages = 0;

        PaginatedResult<MenuItem> paginatedResult = new PaginatedResult<>(content, totalItems, totalPages);

        when(menuItemGateway.findPaginated(size, offset)).thenReturn(paginatedResult);

        // Act
        PaginatedResponseDTO<MenuItemResponseDTO> response = useCase.execute(size, offset);

        // Assert
        assertNotNull(response);
        assertTrue(response.content().isEmpty());
        assertEquals(totalItems, response.totalItems());
        assertEquals(totalPages, response.totalPages());
        assertEquals(offset / size, response.currentPage());
        assertEquals(size, response.pageSize());

        verify(menuItemGateway).findPaginated(size, offset);
        verifyNoInteractions(menuItemMapper);
    }
}
