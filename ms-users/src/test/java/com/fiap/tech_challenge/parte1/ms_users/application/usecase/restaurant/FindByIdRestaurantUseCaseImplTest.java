package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.RestaurantNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.WeekDay;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindByIdRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private AddressGateway addressGateway;

    @Mock
    private OpeningHourGateway openingHourGateway;

    @Mock
    private IRestaurantMapper restaurantMapper;

    @InjectMocks
    private FindByIdRestaurantUseCaseImpl findByIdRestaurantUseCase;

    @Test
    void execute_shouldReturnRestaurantResponseDTO_whenRestaurantExists() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        OpeningHour openingHour = new OpeningHour();
        List<OpeningHour> openingHours = List.of(openingHour);
        RestaurantResponseDTO expectedResponse = new RestaurantResponseDTO(
                UUID.randomUUID(),
                "Teste Restaurante",
                new AddressResponseDTO(
                        UUID.randomUUID(),
                        "01232-000",
                        "Rua Feliz",
                        123,
                        "ap 12",
                        "Alegre",
                        "Rio de Janeiro",
                        "RJ"
                ),
                CuisineType.CHINESA,
                List.of(new OpeningHourResponseDTO(
                        UUID.randomUUID(),
                        WeekDay.QUARTA,
                        LocalTime.parse("08:00:00"),
                        LocalTime.parse("18:00:00")
                ))
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(openingHours);
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(expectedResponse);

        RestaurantResponseDTO result = findByIdRestaurantUseCase.execute(restaurantId);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(restaurantGateway).findById(restaurantId);
        verify(addressGateway).findByRestaurantId(restaurantId);
        verify(openingHourGateway).findByRestaurantId(restaurantId);
        verify(restaurantMapper).toResponseDTO(restaurant);
    }

    @Test
    void execute_shouldThrowRestaurantNotFoundException_whenRestaurantDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> {
            findByIdRestaurantUseCase.execute(restaurantId);
        });

        verify(restaurantGateway).findById(restaurantId);
        verifyNoInteractions(addressGateway, openingHourGateway, restaurantMapper);
    }

    @Test
    void execute_shouldWork_whenAddressIsNotPresent() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        OpeningHour openingHour = new OpeningHour();
        List<OpeningHour> openingHours = List.of(openingHour);
        RestaurantResponseDTO expectedResponse = new RestaurantResponseDTO(
                UUID.randomUUID(),
                "Teste Restaurante",
                new AddressResponseDTO(
                        UUID.randomUUID(),
                        "01232-000",
                        "Rua Feliz",
                        123,
                        "ap 12",
                        "Alegre",
                        "Rio de Janeiro",
                        "RJ"
                ),
                CuisineType.CHINESA,
                List.of(new OpeningHourResponseDTO(
                        UUID.randomUUID(),
                        WeekDay.QUARTA,
                        LocalTime.parse("08:00:00"),
                        LocalTime.parse("18:00:00")
                ))
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(addressGateway.findByRestaurantId(restaurantId)).thenReturn(Optional.empty());
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(openingHours);
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(expectedResponse);

        RestaurantResponseDTO result = findByIdRestaurantUseCase.execute(restaurantId);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(restaurantGateway).findById(restaurantId);
        verify(addressGateway).findByRestaurantId(restaurantId);
        verify(openingHourGateway).findByRestaurantId(restaurantId);
        verify(restaurantMapper).toResponseDTO(restaurant);
    }

    @Test
    void execute_shouldWork_whenOpeningHoursAreEmpty() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        RestaurantResponseDTO expectedResponse = new RestaurantResponseDTO(
                UUID.randomUUID(),
                "Teste Restaurante",
                new AddressResponseDTO(
                        UUID.randomUUID(),
                        "01232-000",
                        "Rua Feliz",
                        123,
                        "ap 12",
                        "Alegre",
                        "Rio de Janeiro",
                        "RJ"
                ),
                CuisineType.CHINESA,
                List.of(new OpeningHourResponseDTO(
                        UUID.randomUUID(),
                        WeekDay.QUARTA,
                        LocalTime.parse("08:00:00"),
                        LocalTime.parse("18:00:00")
                ))
        );

        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of());
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(expectedResponse);

        RestaurantResponseDTO result = findByIdRestaurantUseCase.execute(restaurantId);

        assertNotNull(result);
        assertEquals(expectedResponse, result);

        verify(restaurantGateway).findById(restaurantId);
        verify(addressGateway).findByRestaurantId(restaurantId);
        verify(openingHourGateway).findByRestaurantId(restaurantId);
        verify(restaurantMapper).toResponseDTO(restaurant);
    }
}