package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
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
                            (id, name, email, login, password, last_modified_date, active, user_type_id)
                        VALUES
                            (:id, :name, :email, :login, :password, :last_modified_date, :active, :user_type_id);
                        """)
                .param("id", id)
                .param("name", jdbcUserEntity.getName())
                .param("email", jdbcUserEntity.getEmail())
                .param("login", jdbcUserEntity.getLogin())
                .param("password", jdbcUserEntity.getPassword())
                .param("last_modified_date", jdbcUserEntity.getDateLastChange())
                .param("active", jdbcUserEntity.getActive())
                .param("user_type_id", jdbcUserEntity.getUserTypeId())
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

    public void update(JdbcUserEntity jdbcUserEntity) {
        jdbcClient.sql("""
                            UPDATE users
                            SET name = :name,
                                email = :email,
                                login = :login,
                                last_modified_date = :last_modified_date
                            WHERE id = :id
                        """)
                .param("id", jdbcUserEntity.getId())
                .param("name", jdbcUserEntity.getName())
                .param("email", jdbcUserEntity.getEmail())
                .param("login", jdbcUserEntity.getLogin())
                .param("last_modified_date", new Timestamp(System.currentTimeMillis()))
                .update();
    }

    public boolean existsById(UUID id) {
        return existsByColumn("id", id);
    }

    public boolean emailAlreadyExistsForDifferentUsers(String email, UUID userId) {
        return jdbcClient.sql("""
                            SELECT 1 FROM users
                            WHERE email = :login AND id <> :id
                            LIMIT 1
                        """)
                .param("email", email)
                .param("id", userId)
                .query()
                .optionalValue()
                .isPresent();
    }

    public boolean loginAlreadyExistsForDifferentUsers(String login, UUID userId) {
        return jdbcClient.sql("""
                            SELECT 1 FROM users
                            WHERE login = :login AND id <> :id
                            LIMIT 1
                        """)
                .param("login", login)
                .param("id", userId)
                .query()
                .optionalValue()
                .isPresent();
    }

    public List<User> findAll(int size, int offset) {
        return jdbcClient.sql("""
                         SELECT * FROM users LIMIT :size OFFSET :offset
                        """)
                .param("size", size)
                .param("offset", offset)
                .query(User.class)
                .list();
    }

    public void reactivate(UUID id) {
        jdbcClient.sql("UPDATE users SET active = :active, last_modified_date = :last_modified_date WHERE id = :id")
                .param("id", id)
                .param("active", true)
                .param("last_modified_date", now())
                .update();
    }

    public void deactivate(UUID id) {
        jdbcClient.sql("UPDATE users SET active = :active, last_modified_date = :last_modified_date WHERE id = :id")
                .param("id", id)
                .param("active", false)
                .param("last_modified_date", now())
                .update();
    }

    private Timestamp now() {
        return Timestamp.from(Instant.now());
    }

    public void changePassword(UUID id, String newPasswordEncoded) {
        jdbcClient.sql("UPDATE users SET password = :password, last_modified_date = :last_modified_date WHERE id = :id")
                .param("id", id)
                .param("password", newPasswordEncoded)
                .param("last_modified_date", now())
                .update();
    }

    public boolean existsByEmail(String email) {
        return existsByColumn("email", email);
    }

    public boolean existsByLogin(String login) {
        return existsByColumn("login", login);
    }

    public boolean existsByColumn(String columnName, Object columnValue) {
        String sql = """
                SELECT
                    COUNT(1)
                FROM
                    users
                WHERE %s = :value
                """.formatted(columnName);

        Integer count = jdbcClient.sql(sql)
                .param("value", columnValue)
                .query(Integer.class)
                .single();
        return count > 0;
    }

    public Optional<User> findByLogin(String username) {
        return jdbcClient
                .sql("""
                        SELECT name, email, login, password, last_modified_date, active, id, user_type_id FROM users
                        WHERE login = :login
                        """)
                .param("login", username)
                .query((rs, rowNum) -> {
                    UserType userType = new UserType();
                    userType.setId(rs.getLong("user_type_id"));
                    User user = new User();
                    user.setId(UUID.fromString(rs.getString("id")));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setLogin(rs.getString("login"));
                    user.setPassword(rs.getString("password"));
                    user.setDateLastChange(rs.getTimestamp("last_modified_date"));
                    user.setActive(rs.getBoolean("active"));
                    user.setUserType(userType);
                    return user;
                }).optional();
    }
}
