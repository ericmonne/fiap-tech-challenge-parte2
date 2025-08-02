package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcRestaurantRepository {

    private final JdbcClient jdbcClient;

    public JdbcRestaurantRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


    public UUID save(JdbcRestaurantEntity entity) {
        UUID id = UUID.randomUUID();

        jdbcClient.sql("""
                INSERT INTO restaurants (id, name, cuisine_type, user_id)
                VALUES (:id, :name, :cuisine_type, :user_id)
                """)
                .param("id", id)
                .param("name", entity.getName())
                .param("cuisine_type", entity.getCuisineType().name())
                .param("user_id", entity.getUserId())
                .update();

        return id;
    }

    public Optional<Restaurant> findById(UUID id) {
        return jdbcClient.sql("SELECT * FROM restaurants WHERE id = :id")
                .param("id", id)
                .query(this::toRestaurant)
                .optional();
    }

    public void update(JdbcRestaurantEntity entity) {
        jdbcClient.sql("""
                UPDATE restaurants
                SET name = :name, cuisine_type = :cuisine_type
                WHERE id = :id
                """)
                .param("id", entity.getId())
                .param("name", entity.getName())
                .param("cuisine_type", entity.getCuisineType().name())
                .update();
    }

    private Restaurant toRestaurant(ResultSet rs, int rowNum) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.fromString(rs.getString("id")));
        restaurant.setName(rs.getString("name"));
        restaurant.setCuisineType(CuisineType.valueOf(rs.getString("cuisine_type")));

        User owner = new User();
        owner.setId(UUID.fromString(rs.getString("user_id")));
        restaurant.setUser(owner);

        return restaurant;
    }

    public boolean existsById(UUID restaurantId) {
        Integer count = jdbcClient.sql("SELECT COUNT(*) FROM restaurants WHERE id = :id")
                .param("id", restaurantId)
                .query(Integer.class)
                .single();

        return count > 0;
    }

    public void delete(UUID restaurantId) {
        jdbcClient.sql("""
            DELETE FROM restaurants
            WHERE id = :id
            """)
                .param("id", restaurantId)
                .update();
    }

    public List<Restaurant> findAllByUserId(UUID userId, int size, int offset) {
        return jdbcClient.sql("""
                    SELECT * FROM restaurants
                    WHERE user_id = :user_id
                    LIMIT :size OFFSET :offset
                """)
                .param("user_id", userId)
                .param("size", size)
                .param("offset", offset)
                .query(this::toRestaurant)
                .list();
    }

    public int countByUserIdAndRestaurantId(UUID restaurantId, UUID userId) {
        return jdbcClient.sql("""
            SELECT COUNT(*) FROM restaurants
            WHERE user_id = :user_id AND id = :restaurant_id
            """)
                .param("user_id", userId)
                .param("restaurant_id", restaurantId)
                .query(Integer.class)
                .single();
    }
}