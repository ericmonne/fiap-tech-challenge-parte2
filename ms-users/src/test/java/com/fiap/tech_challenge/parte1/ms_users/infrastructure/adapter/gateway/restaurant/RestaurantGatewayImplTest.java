package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantGatewayImplTest {

    @Mock
    private RestaurantDataSource restaurantDataSource;

    private RestaurantGatewayImpl restaurantGateway;

    @BeforeEach
    void setUp() {
        restaurantGateway = new RestaurantGatewayImpl(restaurantDataSource);
    }

    @Test
    void createRestaurant_ShouldDelegateToDataSourceAndReturnId() {
        Restaurant restaurant = new Restaurant();
        UUID expectedId = UUID.randomUUID();
        when(restaurantDataSource.createRestaurant(restaurant)).thenReturn(expectedId);

        UUID resultId = restaurantGateway.createRestaurant(restaurant);

        assertThat(resultId).isEqualTo(expectedId);
        verify(restaurantDataSource).createRestaurant(restaurant);
    }

    @Test
    void findById_ShouldDelegateToDataSourceAndReturnRestaurant() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant expectedRestaurant = new Restaurant();
        when(restaurantDataSource.findById(restaurantId))
                .thenReturn(Optional.of(expectedRestaurant));

        Optional<Restaurant> result = restaurantGateway.findById(restaurantId);

        assertThat(result).isPresent().contains(expectedRestaurant);
        verify(restaurantDataSource).findById(restaurantId);
    }

    @Test
    void findById_ShouldReturnEmptyWhenDataSourceReturnsEmpty() {
        UUID restaurantId = UUID.randomUUID();
        when(restaurantDataSource.findById(restaurantId)).thenReturn(Optional.empty());

        Optional<Restaurant> result = restaurantGateway.findById(restaurantId);

        assertThat(result).isEmpty();
        verify(restaurantDataSource).findById(restaurantId);
    }

    @Test
    void update_ShouldDelegateToDataSource() {
        Restaurant restaurant = new Restaurant();
        doNothing().when(restaurantDataSource).update(restaurant);

        restaurantGateway.update(restaurant);

        verify(restaurantDataSource).update(restaurant);
    }

    @Test
    void findAllRestaurantsByUserId_ShouldDelegateToDataSource() {
        UUID userId = UUID.randomUUID();
        int size = 10;
        int offset = 0;
        List<Restaurant> expectedList = List.of(new Restaurant());
        when(restaurantDataSource.findAllRestaurantsByUserId(userId, size, offset))
                .thenReturn(expectedList);

        List<Restaurant> result = restaurantGateway.findAllRestaurantsByUserId(userId, size, offset);

        assertThat(result).isEqualTo(expectedList);
        verify(restaurantDataSource).findAllRestaurantsByUserId(userId, size, offset);
    }

    @Test
    void existsById_ShouldDelegateToDataSource() {
        UUID restaurantId = UUID.randomUUID();
        when(restaurantDataSource.existsById(restaurantId)).thenReturn(true);

        boolean exists = restaurantGateway.existsById(restaurantId);

        assertThat(exists).isTrue();
        verify(restaurantDataSource).existsById(restaurantId);
    }

    @Test
    void delete_ShouldDelegateToDataSource() {
        UUID restaurantId = UUID.randomUUID();
        doNothing().when(restaurantDataSource).delete(restaurantId);

        restaurantGateway.delete(restaurantId);

        verify(restaurantDataSource).delete(restaurantId);
    }

    @Test
    void isRestaurantOwnedByUser_ShouldDelegateToDataSource() {
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        when(restaurantDataSource.isRestaurantOwnedByUser(restaurantId, userId))
                .thenReturn(true);

        boolean isOwned = restaurantGateway.isRestaurantOwnedByUser(restaurantId, userId);

        assertThat(isOwned).isTrue();
        verify(restaurantDataSource).isRestaurantOwnedByUser(restaurantId, userId);
    }
}