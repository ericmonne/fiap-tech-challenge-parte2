package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.*;

class FindMenuItemsByRestaurantIdUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private IMenuItemMapper menuItemMapper;

    @InjectMocks
    private FindMenuItemsByRestaurantIdUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldReturnPaginatedMenuItems_whenGatewayReturnsData() {
        // Arrange
        UUID restaurantId = UUID.randomUUID();
        MenuItemsByRestaurantRequestDTO request = new MenuItemsByRestaurantRequestDTO(restaurantId, 2, 0);

        MenuItem menuItem1 = mock(MenuItem.class);
        MenuItem menuItem2 = mock(MenuItem.class);
        List<MenuItem> domainItems = List.of(menuItem1, menuItem2);
        PaginatedResult<MenuItem> paginatedResult = new PaginatedResult<>(domainItems, 2, 1);

        MenuItemResponseDTO dto1 = mock(MenuItemResponseDTO.class);
        MenuItemResponseDTO dto2 = mock(MenuItemResponseDTO.class);

        when(menuItemGateway.findByRestaurantId(request)).thenReturn(paginatedResult);
        when(menuItemMapper.toResponseDTO(menuItem1)).thenReturn(dto1);
        when(menuItemMapper.toResponseDTO(menuItem2)).thenReturn(dto2);

        // Act
        PaginatedResponseDTO<MenuItemResponseDTO> results = useCase.execute(request);

        // Assert
        assertThat(results)
                .isNotNull()
                .satisfies(result -> {
                    assertThat(result.content()).containsExactly(dto1, dto2);
                    assertThat(result.totalItems()).isEqualTo(2);
                    assertThat(result.totalPages()).isEqualTo(1);
                    assertThat(result.currentPage()).isZero();
                    assertThat(result.pageSize()).isEqualTo(2);
                });

        verify(menuItemGateway).findByRestaurantId(request);
        verify(menuItemMapper).toResponseDTO(menuItem1);
        verify(menuItemMapper).toResponseDTO(menuItem2);
    }

    @Test
    void execute_shouldReturnEmptyResult_whenGatewayReturnsEmptyList() {
        // Arrange
        UUID restaurantId = UUID.randomUUID();
        MenuItemsByRestaurantRequestDTO request = new MenuItemsByRestaurantRequestDTO(restaurantId, 1, 0);
        PaginatedResult<MenuItem> paginatedResult = new PaginatedResult<>(List.of(), 0, 0);

        when(menuItemGateway.findByRestaurantId(request)).thenReturn(paginatedResult);

        // Act
        PaginatedResponseDTO<MenuItemResponseDTO> results = useCase.execute(request);

        // Assert
        assertThat(results)
                .isNotNull()
                .satisfies(result -> {
                    assertThat(result.content()).isEmpty();
                    assertThat(result.totalItems()).isZero();
                    assertThat(result.totalPages()).isZero();
                    assertThat(result.currentPage()).isZero();
                    assertThat(result.pageSize()).isEqualTo(1);
                });

        verify(menuItemGateway).findByRestaurantId(request);
        verifyNoInteractions(menuItemMapper);
    }

    @Test
    void execute_shouldThrowRuntimeException_whenGatewayFailsUnexpectedly() {
        // Arrange
        UUID restaurantId = UUID.randomUUID();
        MenuItemsByRestaurantRequestDTO request = new MenuItemsByRestaurantRequestDTO(restaurantId, 1, 10);

        RuntimeException unexpected = new RuntimeException("Unexpected DB error");
        when(menuItemGateway.findByRestaurantId(request)).thenThrow(unexpected);

        // Act & Assert
        RuntimeException thrown = catchThrowableOfType(() -> useCase.execute(request), RuntimeException.class);

        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unexpected DB error");

        verify(menuItemGateway).findByRestaurantId(request);
        verifyNoInteractions(menuItemMapper);
    }

    @Test
    void execute_shouldHandleNullResponseSafely() {
        // Arrange
        UUID restaurantId = UUID.randomUUID();
        MenuItemsByRestaurantRequestDTO request = new MenuItemsByRestaurantRequestDTO(restaurantId, 1, 10);

        when(menuItemGateway.findByRestaurantId(request)).thenReturn(null);

        // Act & Assert
        NullPointerException thrown = catchThrowableOfType(() -> useCase.execute(request), NullPointerException.class);

        assertThat(thrown).isInstanceOf(NullPointerException.class);

        verify(menuItemGateway).findByRestaurantId(request);
        verifyNoInteractions(menuItemMapper);
    }
}
