package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcOpeningHourRepository {
    private final JdbcClient jdbcClient;

    public JdbcOpeningHourRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public UUID save(JdbcOpeningHourEntity entity) {
        UUID id = UUID.randomUUID();

        jdbcClient.sql("""
                INSERT INTO restaurant_opening_hour (id, restaurant_id, weekday, opening_time, closing_time)
                VALUES (:id, :restaurant_id, :weekday, :opening_time, :closing_time)
            """)
                .param("id", id)
                .param("restaurant_id", entity.getRestaurantId())
                .param("weekday", entity.getWeekDay().name())
                .param("opening_time", entity.getOpeningTime())
                .param("closing_time", entity.getClosingTime())
                .update();

        return id;
    }

    public List<OpeningHour> findByRestaurantId(UUID restaurantId) {
        return jdbcClient.sql("""
                SELECT * FROM restaurant_opening_hour WHERE restaurant_id = :restaurant_id
            """)
                .param("restaurant_id", restaurantId)
                .query(this::mapRowToOpeningHour)
                .list();
    }

    public Optional<OpeningHour> findById(UUID id) {
        return jdbcClient.sql("SELECT * FROM restaurant_opening_hour WHERE id = :id")
                .param("id", id)
                .query(this::mapRowToOpeningHour)
                .optional();
    }

    public void update(JdbcOpeningHourEntity entity) {
        jdbcClient.sql("""
                UPDATE restaurant_opening_hour
                SET weekday = :weekday,
                    opening_time = :opening_time,
                    closing_time = :closing_time
                WHERE id = :id
            """)
                .param("id", entity.getId())
                .param("weekday", entity.getWeekDay().name())
                .param("opening_time", entity.getOpeningTime())
                .param("closing_time", entity.getClosingTime())
                .update();
    }

    private OpeningHour mapRowToOpeningHour(ResultSet rs, int rowNum) throws SQLException {
        return new OpeningHour(
                UUID.fromString(rs.getString("id")),
                WeekDay.valueOf(rs.getString("weekday")),
                rs.getTime("opening_time").toLocalTime(),
                rs.getTime("closing_time").toLocalTime(),
                null
        );
    }
}
