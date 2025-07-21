package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcUserRepository {
    private final JdbcClient jdbcClient;

    public JdbcUserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Saves a new jdbcUserEntity entity in the database.
     *
     * @param jdbcUserEntity the User entity to save
     * @return the UUID assigned to the newly saved jdbcUserEntity
     */
    public UUID save(JdbcUserEntity jdbcUserEntity) {

        UUID id = UUID.randomUUID();

        jdbcClient.sql("""
                        INSERT INTO users
                            (id, name, email, login, password, last_modified_date, active, role)
                        VALUES
                            (:id, :name, :email, :login, :password, :last_modified_date, :active, CAST(:role AS role_type));
                        """)
                .param("id", id)
                .param("name", jdbcUserEntity.getName())
                .param("email", jdbcUserEntity.getEmail())
                .param("login", jdbcUserEntity.getLogin())
                .param("password", jdbcUserEntity.getPassword())
                .param("last_modified_date", jdbcUserEntity.getDateLastChange())
                .param("active", jdbcUserEntity.getActive())
                .param("role", jdbcUserEntity.getRole())
                .update();

        return id;
    }

    public Optional<User> findById(UUID id) {
        return jdbcClient.sql("""
                        SELECT * FROM users
                        WHERE id = :id
                        """)
                .param("id", id)
                .query(User.class)
                .optional();
    }

}
