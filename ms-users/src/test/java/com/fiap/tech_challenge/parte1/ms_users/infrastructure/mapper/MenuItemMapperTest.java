package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item.JdbcMenuItemEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuItemMapperTest {

    private final MenuItemMapper mapper = new MenuItemMapper();

    @Test
    void shouldMapListToResponseDTOList() {
        MenuItem item = createMenuItem();
        List<MenuItemResponseDTO> result = mapper.mapList(List.of(item));

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo(item.getName());
    }

    @Test
    void shouldReturnEmptyListWhenMappingNullList() {
        assertThat(mapper.mapList(null)).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenMappingEmptyList() {
        assertThat(mapper.mapList(List.of())).isEmpty();
    }

    @Test
    void shouldMapMenuItemToResponseDTO() {
        MenuItem item = createMenuItem();

        MenuItemResponseDTO dto = mapper.toResponseDTO(item);

        assertThat(dto).isNotNull();
        assertThat(dto.name()).isEqualTo(item.getName());
        assertThat(dto.restaurantId()).isEqualTo(item.getRestaurantId());
    }

    @Test
    void shouldReturnNullWhenMenuItemIsNull() {
        assertThat(mapper.toResponseDTO(null)).isNull();
    }

    @Test
    void shouldMapCreateDTOToMenuItem() {
        UUID restaurantId = UUID.randomUUID();
        CreateMenuItemDTO dto = new CreateMenuItemDTO(
                "Pizza",
                "Delicious",
                new BigDecimal("29.99"),
                true,
                "/img/pizza.jpg",
                restaurantId
        );

        MenuItem item = mapper.toEntity(dto);

        assertThat(item).isNotNull();
        assertThat(item.getName()).isEqualTo(dto.name());
        assertThat(item.getRestaurantId()).isEqualTo(dto.restaurantId());
    }

    @Test
    void shouldMapUpdateDTOToMenuItem() {
        // Arrange
        UpdateMenuItemDTO dto = new UpdateMenuItemDTO(
                "Burger",
                "Tasty",
                new BigDecimal("19.99"),
                false,
                "/img/burger.jpg"
        );

        MenuItem existing = new MenuItem(
                UUID.randomUUID(),
                "Old Burger",
                "Old Desc",
                new BigDecimal("9.99"),
                true,
                "/img/old.jpg",
                UUID.randomUUID()
        );

        // Act
        MenuItem result = mapper.toEntity(dto, existing);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Burger");
        assertThat(result.getDescription()).isEqualTo("Tasty");
        assertThat(result.getPrice()).isEqualByComparingTo("19.99");
        assertThat(result.getAvailableOnlyOnSite()).isFalse();
        assertThat(result.getImagePath()).isEqualTo("/img/burger.jpg");
        assertThat(result.getId()).isEqualTo(existing.getId());
        assertThat(result.getRestaurantId()).isEqualTo(existing.getRestaurantId());
    }


    @Test
    void shouldMapMenuItemToJdbcEntity() {
        MenuItem item = createMenuItem();

        JdbcMenuItemEntity entity = mapper.toJdbcMenuItemEntity(item);

        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo(item.getName());
        assertThat(entity.getRestaurantId()).isEqualTo(item.getRestaurantId());
    }

    private MenuItem createMenuItem() {
        MenuItem item = new MenuItem();
        item.setId(UUID.randomUUID());
        item.setName("Sushi");
        item.setDescription("Fresh fish");
        item.setPrice(new BigDecimal("39.99"));
        item.setAvailableOnlyOnSite(true);
        item.setImagePath("/img/sushi.jpg");
        item.setRestaurantId(UUID.randomUUID());
        return item;
    }
}
