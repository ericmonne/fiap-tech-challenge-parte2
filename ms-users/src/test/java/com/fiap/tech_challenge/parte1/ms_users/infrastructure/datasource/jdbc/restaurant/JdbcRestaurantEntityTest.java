package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JdbcRestaurantEntityTest {

    private JdbcRestaurantEntity entity;
    private UUID testId;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        entity = new JdbcRestaurantEntity();
        testId = UUID.randomUUID();
        testUserId = UUID.randomUUID();
    }

    @Test
    void shouldCreateEntityWithAllArgsConstructor() {
        JdbcRestaurantEntity entity = new JdbcRestaurantEntity();
        entity.setId(testId);
        entity.setName("Test Restaurant");
        entity.setCuisineType(CuisineType.ITALIANA);
        entity.setUserId(testUserId);

        assertNotNull(entity);
        assertEquals(testId, entity.getId());
        assertEquals("Test Restaurant", entity.getName());
        assertEquals(CuisineType.ITALIANA, entity.getCuisineType());
        assertEquals(testUserId, entity.getUserId());
    }

    @Test
    void shouldSetAndGetId() {
        entity.setId(testId);
        assertEquals(testId, entity.getId());
    }

    @Test
    void shouldSetAndGetName() {
        String testName = "Test Restaurant";
        entity.setName(testName);
        assertEquals(testName, entity.getName());
    }

    @Test
    void shouldSetAndGetCuisineType() {
        entity.setCuisineType(CuisineType.JAPONESA);
        assertEquals(CuisineType.JAPONESA, entity.getCuisineType());
    }

    @Test
    void shouldSetAndGetUserId() {
        entity.setUserId(testUserId);
        assertEquals(testUserId, entity.getUserId());
    }

    @Test
    void shouldBeEqualWhenSameInstance() {
        assertTrue(entity.equals(entity));
    }

    @Test
    void shouldNotBeEqualWhenDifferentClass() {
        assertFalse(entity.equals(new Object()));
    }

    @Test
    void shouldHaveConsistentHashCode() {
        entity.setId(testId);
        entity.setName("Test");
        entity.setCuisineType(CuisineType.BRASILEIRA);
        entity.setUserId(testUserId);

        int initialHashCode = entity.hashCode();
        assertEquals(initialHashCode, entity.hashCode());
    }
}