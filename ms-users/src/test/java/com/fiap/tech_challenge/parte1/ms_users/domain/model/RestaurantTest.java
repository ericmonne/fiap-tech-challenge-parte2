package com.fiap.tech_challenge.parte1.ms_users.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantTest {

    @Test
    void shouldCreateEmptyRestaurant() {
        Restaurant restaurant = new Restaurant();

        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getId()).isNull();
        assertThat(restaurant.getName()).isNull();
        assertThat(restaurant.getAddress()).isNull();
        assertThat(restaurant.getCuisineType()).isNull();
        assertThat(restaurant.getOpeningHours()).isNull();
        assertThat(restaurant.getUser()).isNull();
    }

    @Test
    void shouldCreateRestaurantWithAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        String name = "Test Restaurant";
        Address address = new Address();
        CuisineType cuisineType = CuisineType.ITALIANA;
        List<OpeningHour> openingHours = List.of(new OpeningHour());
        User user = new User();

        Restaurant restaurant = new Restaurant(id, name, address, cuisineType, openingHours, user);

        assertThat(restaurant.getId()).isEqualTo(id);
        assertThat(restaurant.getName()).isEqualTo(name);
        assertThat(restaurant.getAddress()).isEqualTo(address);
        assertThat(restaurant.getCuisineType()).isEqualTo(cuisineType);
        assertThat(restaurant.getOpeningHours()).isEqualTo(openingHours);
        assertThat(restaurant.getUser()).isEqualTo(user);
    }

    @Test
    void shouldSetAndGetProperties() {
        Restaurant restaurant = new Restaurant();
        UUID id = UUID.randomUUID();
        String name = "Test Restaurant";
        Address address = new Address();
        CuisineType cuisineType = CuisineType.JAPONESA;
        List<OpeningHour> openingHours = List.of(new OpeningHour());
        User user = new User();

        restaurant.setId(id);
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setCuisineType(cuisineType);
        restaurant.setOpeningHours(openingHours);
        restaurant.setUser(user);

        assertThat(restaurant.getId()).isEqualTo(id);
        assertThat(restaurant.getName()).isEqualTo(name);
        assertThat(restaurant.getAddress()).isEqualTo(address);
        assertThat(restaurant.getCuisineType()).isEqualTo(cuisineType);
        assertThat(restaurant.getOpeningHours()).isEqualTo(openingHours);
        assertThat(restaurant.getUser()).isEqualTo(user);
    }

    @Test
    void shouldHandleNullCollectionsInConstructor() {
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), "Test", null, null, null, null);

        assertThat(restaurant.getAddress()).isNull();
        assertThat(restaurant.getCuisineType()).isNull();
        assertThat(restaurant.getOpeningHours()).isNull();
        assertThat(restaurant.getUser()).isNull();
    }

    @Test
    void shouldHandleNullCollectionsInSetters() {
        Restaurant restaurant = new Restaurant();

        restaurant.setAddress(null);
        restaurant.setCuisineType(null);
        restaurant.setOpeningHours(null);
        restaurant.setUser(null);

        assertThat(restaurant.getAddress()).isNull();
        assertThat(restaurant.getCuisineType()).isNull();
        assertThat(restaurant.getOpeningHours()).isNull();
        assertThat(restaurant.getUser()).isNull();
    }
}