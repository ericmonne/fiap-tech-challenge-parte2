package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.restaurant.RestaurantGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantBeanConfigTest {

    @Mock
    private JdbcRestaurantRepository jdbcRestaurantRepository;

    @Mock
    private IRestaurantMapper restaurantMapper;

    @Mock
    private RestaurantDataSource restaurantDataSource;

    private RestaurantGatewayImpl restaurantGateway;

    @BeforeEach
    void setUp() {
        restaurantGateway = new RestaurantGatewayImpl(restaurantDataSource);
    }

    @Test
    void testSaveShouldCallDataSourceAndReturnRestaurantId() {
        Restaurant restaurant = mock(Restaurant.class);
        UUID expectedId = UUID.randomUUID();
        when(restaurantDataSource.createRestaurant(restaurant)).thenReturn(expectedId);

        UUID resultId = restaurantGateway.createRestaurant(restaurant);

        assertThat(resultId).isEqualTo(expectedId);
        verify(restaurantDataSource).createRestaurant(restaurant);
    }

    @Test
    void testFindByIdShouldCallDataSourceAndReturnRestaurant() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant expectedRestaurant = mock(Restaurant.class);
        when(restaurantDataSource.findById(restaurantId))
                .thenReturn(Optional.of(expectedRestaurant));

        Optional<Restaurant> result = restaurantGateway.findById(restaurantId);

        assertThat(result).isPresent().contains(expectedRestaurant);
        verify(restaurantDataSource).findById(restaurantId);
    }

    @Test
    void testDeleteShouldCallDataSource() {
        UUID restaurantId = UUID.randomUUID();
        doNothing().when(restaurantDataSource).delete(restaurantId);

        restaurantGateway.delete(restaurantId);

        verify(restaurantDataSource).delete(restaurantId);
    }
}