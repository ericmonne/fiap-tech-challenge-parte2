package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//TODO - NOT WORKING DUE TO LACK OF MIGRATION

@JdbcTest
@Import(JdbcMenuItemRepository.class)
@ActiveProfiles("test")
class JdbcMenuItemRepositoryTest {

    @Autowired
    private JdbcMenuItemRepository repository;

    @Autowired
    private DataSource dataSource;

    private UUID restaurantId;

    @BeforeEach
    void setup() {
        restaurantId = UUID.randomUUID();

        JdbcClient jdbcClient = JdbcClient.create(dataSource);
        jdbcClient.sql("""
            INSERT INTO menu_items (id, name, description, price, available_only_on_site, image_path, restaurant_id)
            VALUES (:id, :name, :description, :price, :onlyOnSite, :image, :restaurantId)
        """)
                .param("id", UUID.randomUUID())
                .param("name", "Pizza")
                .param("description", "Delicious pizza")
                .param("price", new BigDecimal("49.90"))
                .param("onlyOnSite", true)
                .param("image", "/images/pizza.jpg")
                .param("restaurantId", restaurantId)
                .update();
    }

    @Test
    void shouldReturnAllItems() {
        List<MenuItem> items = repository.findAll();
        assertThat(items).isNotEmpty();
    }

    @Test
    void shouldReturnItemByRestaurantId() {
        var requestDTO = new MenuItemsByRestaurantRequestDTO(restaurantId, 10, 0);
        PaginatedResult<MenuItem> result = repository.findByRestaurantId(requestDTO);
        assertThat(result.content()).isNotEmpty();
        assertThat(result.totalItems()).isGreaterThan(0);
    }

    @Test
    void shouldReturnItemByName() {
        Optional<MenuItem> item = repository.findByName("Pizza");
        assertThat(item).isPresent();
    }

    @Test
    void shouldReturnFalseIfItemDoesNotExistById() {
        boolean exists = repository.existsById(UUID.randomUUID());
        assertThat(exists).isFalse();
    }
}
