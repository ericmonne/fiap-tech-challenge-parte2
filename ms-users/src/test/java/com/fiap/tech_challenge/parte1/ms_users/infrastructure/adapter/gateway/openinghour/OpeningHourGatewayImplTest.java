package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpeningHourGatewayImplTest {

    @Mock
    private OpeningHourDataSource openingHourDataSource;

    @InjectMocks
    private OpeningHourGatewayImpl openingHourGateway;

    private OpeningHour openingHour;
    private Restaurant restaurant;
    private UUID openingHourId;
    private UUID restaurantId;

    @BeforeEach
    void setUp() {
        openingHourId = UUID.randomUUID();
        restaurantId = UUID.randomUUID();
        restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        openingHour = new OpeningHour(
                openingHourId,
                WeekDay.SABADO,
                LocalTime.parse("08:00:00"),
                LocalTime.parse("18:00:00"),
                restaurant);
    }

    @Test
    void createOpeningHour_ShouldReturnUUID_WhenSuccessful() {
        when(openingHourDataSource.createOpeningHour(openingHour)).thenReturn(openingHourId);

        UUID result = openingHourGateway.createOpeningHour(openingHour);

        assertEquals(openingHourId, result);
        verify(openingHourDataSource, times(1)).createOpeningHour(openingHour);
    }

    @Test
    void findById_ShouldReturnOpeningHour_WhenExists() {
        when(openingHourDataSource.findById(openingHourId)).thenReturn(Optional.of(openingHour));

        Optional<OpeningHour> result = openingHourGateway.findById(openingHourId);

        assertTrue(result.isPresent());
        assertEquals(openingHour, result.get());
        verify(openingHourDataSource, times(1)).findById(openingHourId);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        when(openingHourDataSource.findById(openingHourId)).thenReturn(Optional.empty());

        Optional<OpeningHour> result = openingHourGateway.findById(openingHourId);

        assertTrue(result.isEmpty());
        verify(openingHourDataSource, times(1)).findById(openingHourId);
    }

    @Test
    void update_ShouldCallDataSourceUpdate() {
        openingHourGateway.update(openingHour);
        verify(openingHourDataSource, times(1)).update(openingHour);
    }

    @Test
    void findByRestaurantId_ShouldReturnListOfOpeningHours() {
        List<OpeningHour> expected = List.of(openingHour);
        when(openingHourDataSource.findByRestaurantId(restaurantId)).thenReturn(expected);

        List<OpeningHour> result = openingHourGateway.findByRestaurantId(restaurantId);

        assertEquals(expected, result);
        verify(openingHourDataSource, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    void deleteByRestaurantId_ShouldCallDataSourceDelete() {
        openingHourGateway.deleteByRestaurantId(restaurantId);

        verify(openingHourDataSource, times(1)).deleteByRestaurantId(restaurantId);
    }
}