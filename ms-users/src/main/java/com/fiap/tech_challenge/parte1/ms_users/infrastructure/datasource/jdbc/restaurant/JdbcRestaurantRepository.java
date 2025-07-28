package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcRestaurantRepository {

    private final JdbcClient jdbcClient;

    public JdbcRestaurantRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    public UUID save(JdbcRestaurantEntity restaurant) {
        UUID id = UUID.randomUUID();
        jdbcClient.sql("""
                INSERT INTO restaurants (id, name, owner_id)
                VALUES (:id, :name, :ownerId)
            """)
                .param("id", id)
                .param("name", restaurant.getName())
                .param("ownerId", restaurant.getUserId())
                .update();
        return id;
    }

    public void update(JdbcRestaurantEntity restaurant) {
        jdbcClient.sql("""
                UPDATE restaurants
                SET name = :name, owner_id = :ownerId
                WHERE id = :id
                """)
                .param("id", restaurant.getId())
                .param("name", restaurant.getName())
                .param("ownerId", restaurant.getUserId())
                .update();
    }

    public void delete(UUID id) {
        jdbcClient.sql("DELETE FROM restaurants WHERE id = :id")
                .param("id", id)
                .update();
    }

    public Optional<Restaurant> findById(UUID id) {
        return jdbcClient.sql("SELECT * FROM restaurants WHERE id = :id")
                .param("id", id)
                .query(Restaurant.class)
                .optional();
    }

    public List<Restaurant> findAll(int size, int offset) {
        return jdbcClient.sql("""
                SELECT * FROM restaurants
                ORDER BY name
                LIMIT :size OFFSET :offset
                """)
                .param("size", size)
                .param("offset", offset)
                .query(Restaurant.class)
                .list();
    }

    public boolean existsById(UUID id) {
        Integer count = jdbcClient.sql("SELECT COUNT(1) FROM restaurants WHERE id = :id")
                .param("id", id)
                .query(Integer.class)
                .single();
        return count > 0;
    }

    public List<Restaurant> findByUserId(UUID userId) {
        return jdbcClient.sql("SELECT * FROM restaurants WHERE owner_id = :userId")
                .param("userId", userId)
                .query(Restaurant.class)
                .list();
    }
}
