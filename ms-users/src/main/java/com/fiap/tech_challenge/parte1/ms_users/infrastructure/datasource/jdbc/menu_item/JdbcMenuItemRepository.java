package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcMenuItemRepository {
    private final JdbcClient jdbcClient;

    public JdbcMenuItemRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<MenuItem> findAll() {
        final String sql = """
                SELECT
                    name,
                    description,
                    price,
                    available_only_on_site,
                    image_path
                FROM
                    menu_items
                """;

        return jdbcClient
                .sql(sql)
                .query(MenuItem.class)
                .list();
    }

    public PaginatedResult<MenuItem> findAllPaginated(int size, int offset) {
        final String sql = """
                         SELECT
                             name,
                             description,
                             price,
                             available_only_on_site,
                             image_path
                         FROM
                             menu_items
                         ;
                 LIMIT :size
                 OFFSET :offset
                """;

        List<MenuItem> items = jdbcClient.sql(sql)
                .param("size", size)
                .param("offset", offset)
                .query(MenuItem.class)
                .list();

        final String sqlTotalCount = """
                        SELECT
                            COUNT(*)
                        FROM
                            menu_items
                        ;
                """;
        Integer totalItems = jdbcClient.sql(sqlTotalCount)
                .query(Integer.class)
                .single();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        return new PaginatedResult<>(items, totalItems, totalPages);
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public MenuItem save(JdbcMenuItemEntity jdbcMenuItemEntity) {
        UUID id = UUID.randomUUID();
        final String sql = """
                    INSERT INTO menu_items (
                        id, name, description, price, available_only_on_site, image_path, restaurant_id
                    ) VALUES (
                        :id, :name, :description, :price, :availableOnlyOnSite, :imagePath, :restaurantId
                    );
                """;
        jdbcClient.sql(sql)
                .param("id", id)
                .param("name", jdbcMenuItemEntity.getName())
                .param("description", jdbcMenuItemEntity.getDescription())
                .param("price", jdbcMenuItemEntity.getPrice())
                .param("availableOnlyOnSite", jdbcMenuItemEntity.getAvailableOnlyOnSite())
                .param("imagePath", jdbcMenuItemEntity.getImagePath())
                .param("restaurantId", jdbcMenuItemEntity.getRestaurantId())
                .update();

        return new MenuItem(id, jdbcMenuItemEntity.getName(), jdbcMenuItemEntity.getDescription(), jdbcMenuItemEntity.getPrice(), jdbcMenuItemEntity.getAvailableOnlyOnSite(), jdbcMenuItemEntity.getImagePath(), jdbcMenuItemEntity.getRestaurantId());

    }

    public Optional<MenuItem> findById(UUID id) {
        final String sql = "SELECT * FROM menu_items WHERE id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(MenuItem.class)
                .optional();
    }

    public void deleteById(UUID id) {
        final String sql = "DELETE FROM menu_items WHERE id = :id";
        jdbcClient.sql(sql)
                .param("id", id)
                .update();
    }

    public void update(JdbcMenuItemEntity jdbcMenuItemEntity) {
        final String sql = """
                UPDATE menu_items
                SET name = :name, description = :description, price = :price, available_only_on_site = :availableOnlyOnSite, image_path = :imagePath
                WHERE id = :id
                """;
        jdbcClient.sql(sql)
                .param("id", jdbcMenuItemEntity.getId())
                .param("name", jdbcMenuItemEntity.getName())
                .param("description", jdbcMenuItemEntity.getDescription())
                .param("price", jdbcMenuItemEntity.getPrice())
                .param("availableOnlyOnSite", jdbcMenuItemEntity.getAvailableOnlyOnSite())
                .param("imagePath", jdbcMenuItemEntity.getImagePath())
                .update();
    }

    public boolean existsById(UUID id) {
        return existsByColumn("id", id);
    }

    private boolean existsByColumn(String columnName, Object columnValue) {
        String sql = """
                SELECT
                    COUNT(1)
                FROM
                    menu_items
                WHERE %s = :value
                """.formatted(columnName);

        Integer count = jdbcClient.sql(sql)
                .param("value", columnValue)
                .query(Integer.class)
                .single();
        return count > 0;
    }

    public Optional<MenuItem> findByName(String name) {
        final String sql = "SELECT * FROM menu_items WHERE name = :name";
        return jdbcClient.sql(sql)
                .param("name", name)
                .query(MenuItem.class)
                .optional();
    }


    /**
     * Retrieves a paginated list of menu items for a specific restaurant.
     *
     * @param restaurantRequestDTO The request containing the restaurant ID and pagination parameters.
     * @return A paginated result containing the list of menu items and pagination metadata.
     */
    public PaginatedResult<MenuItem> findByRestaurantId(MenuItemsByRestaurantRequestDTO restaurantRequestDTO) {

        UUID restaurantId = restaurantRequestDTO.restaurantId();
        int size = restaurantRequestDTO.size();
        int offset = restaurantRequestDTO.offset();
        final String sql = """
                SELECT name, description, price, available_only_on_site, image_path, restaurant_id
                FROM menu_items
                WHERE restaurant_id = :restaurant_id
                ORDER BY name
                LIMIT :size OFFSET :offset
                """;

        List<MenuItem> items = jdbcClient.sql(sql)
                .param("restaurant_id", restaurantId)
                .param("size", size)
                .param("offset", offset)
                .query(MenuItem.class)
                .list();


        final String countSql = """
                    SELECT COUNT(*)
                    FROM menu_items
                    WHERE restaurant_id = :restaurant_id
                """;

        int totalItems = jdbcClient.sql(countSql)
                .param("restaurant_id", restaurantId)
                .query(Integer.class)
                .single();

        int totalPages = size > 0 ? (int) Math.ceil((double) totalItems / size) : 0;

        return new PaginatedResult<>(items, totalItems, totalPages);
    }


}
