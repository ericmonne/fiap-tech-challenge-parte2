package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private AddressGateway addressGateway;

    @Mock
    private OpeningHourGateway openingHourGateway;

    @Mock
    private IRestaurantMapper restaurantMapper;

    @Mock
    private List<OpeningHourValidator> openingHourValidators;

    @InjectMocks
    private RegisterRestaurantUseCaseImpl registerRestaurantUseCase;

    @Test
    void execute_shouldRegisterRestaurantSuccessfully() {
        UUID restaurantId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UUID openingHourId = UUID.randomUUID();

        Restaurant restaurant = new Restaurant();
        restaurant.setName("Teste Restaurante");
        restaurant.setCuisineType(CuisineType.CHINESA);

        Address address = new Address();
        address.setZipcode("01232-000");
        address.setStreet("Rua Feliz");
        address.setNumber(123);
        address.setComplement("ap 12");
        address.setNeighborhood("Alegre");
        address.setCity("Rio de Janeiro");
        address.setState("RJ");
        restaurant.setAddress(address);

        OpeningHour openingHour = new OpeningHour();
        openingHour.setWeekDay(WeekDay.QUARTA);
        openingHour.setOpeningTime(LocalTime.parse("08:00:00"));
        openingHour.setClosingTime(LocalTime.parse("18:00:00"));
        restaurant.setOpeningHours(List.of(openingHour));

        RestaurantResponseDTO expectedResponse = new RestaurantResponseDTO(
                restaurantId,
                "Teste Restaurante",
                new AddressResponseDTO(
                        addressId,
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
                        openingHourId,
                        WeekDay.QUARTA,
                        LocalTime.parse("08:00:00"),
                        LocalTime.parse("18:00:00")
                ))
        );

        when(restaurantGateway.createRestaurant(any(Restaurant.class))).thenReturn(restaurantId);
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(addressGateway.findByRestaurantId(restaurantId)).thenReturn(Optional.of(address));
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(openingHour));
        when(restaurantMapper.toResponseDTO(any(Restaurant.class))).thenReturn(expectedResponse);
        when(openingHourValidators.iterator()).thenReturn(Collections.emptyIterator());

        RestaurantResponseDTO result = registerRestaurantUseCase.execute(restaurant);

        assertNotNull(result);
        assertEquals(restaurantId, result.id());
        assertEquals("Teste Restaurante", result.name());
        assertEquals(CuisineType.CHINESA, result.cuisineType());

        assertNotNull(result.address());
        assertEquals(addressId, result.address().id());
        assertEquals("01232-000", result.address().zipcode());

        assertEquals(1, result.openingHours().size());
        assertEquals(WeekDay.QUARTA, result.openingHours().get(0).weekDay());
        assertEquals(LocalTime.parse("08:00:00"), result.openingHours().get(0).openingTime());
        assertEquals(LocalTime.parse("18:00:00"), result.openingHours().get(0).closingTime());

        verify(restaurantGateway).createRestaurant(restaurant);
        verify(addressGateway).saveRestaurantAddress(address, restaurantId);
        verify(openingHourGateway).createOpeningHour(openingHour);
        verify(restaurantGateway).findById(restaurantId);
        verify(addressGateway).findByRestaurantId(restaurantId);
        verify(openingHourGateway).findByRestaurantId(restaurantId);
        verify(restaurantMapper).toResponseDTO(restaurant);
    }

    @Test
    void execute_shouldWork_whenNoOpeningHoursProvided() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Restaurante sem horários");
        restaurant.setOpeningHours(Collections.emptyList());

        RestaurantResponseDTO expectedResponse = new RestaurantResponseDTO(
                restaurantId,
                "Restaurante sem horários",
                null,
                null,
                Collections.emptyList()
        );

        when(restaurantGateway.createRestaurant(any(Restaurant.class))).thenReturn(restaurantId);
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toResponseDTO(any(Restaurant.class))).thenReturn(expectedResponse);
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(Collections.emptyList());

        RestaurantResponseDTO result = registerRestaurantUseCase.execute(restaurant);

        assertNotNull(result);
        assertEquals(restaurantId, result.id());
        assertEquals("Restaurante sem horários", result.name());
        assertTrue(result.openingHours().isEmpty());

        verify(restaurantGateway).createRestaurant(restaurant);
        verify(restaurantGateway).findById(restaurantId);
        verify(openingHourGateway).findByRestaurantId(restaurantId);
        verify(restaurantMapper).toResponseDTO(restaurant);
        verifyNoMoreInteractions(openingHourGateway);
    }

    @Test
    void execute_shouldValidateOpeningHours() {
        UUID restaurantId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Restaurante com validação");

        OpeningHour openingHour = new OpeningHour();
        openingHour.setWeekDay(WeekDay.SEGUNDA);
        openingHour.setOpeningTime(LocalTime.parse("08:00"));
        openingHour.setClosingTime(LocalTime.parse("18:00"));
        restaurant.setOpeningHours(List.of(openingHour));

        OpeningHourValidator validator = mock(OpeningHourValidator.class);
        when(openingHourValidators.iterator()).thenReturn(List.of(validator).iterator());

        when(restaurantGateway.createRestaurant(any())).thenReturn(restaurantId);
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toResponseDTO((Restaurant) any())).thenReturn(mock(RestaurantResponseDTO.class));
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(openingHour));

        registerRestaurantUseCase.execute(restaurant);

        verify(validator).validate(openingHour);
    }
}