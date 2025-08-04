package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.ForbiddenOperationException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.RestaurantNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseImplTest {

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
    private UpdateRestaurantUseCaseImpl updateRestaurantUseCase;

    @Test
    void execute_shouldUpdateRestaurantSuccessfully() {
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Restaurante Atualizado");

        Address address = new Address();
        restaurant.setAddress(address);

        OpeningHour openingHour = new OpeningHour();
        restaurant.setOpeningHours(List.of(openingHour));

        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setId(restaurantId);
        updatedRestaurant.setAddress(address);
        updatedRestaurant.setOpeningHours(List.of(openingHour));

        RestaurantResponseDTO expectedResponse = new RestaurantResponseDTO(
                restaurantId,
                "Restaurante Atualizado",
                null,
                null,
                Collections.emptyList()
        );

        when(restaurantGateway.existsById(restaurantId)).thenReturn(true);
        when(restaurantGateway.isRestaurantOwnedByUser(restaurantId, userId)).thenReturn(true);
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(updatedRestaurant));
        when(addressGateway.findByRestaurantId(restaurantId)).thenReturn(Optional.of(address));
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(openingHour));
        when(restaurantMapper.toResponseDTO(updatedRestaurant)).thenReturn(expectedResponse);
        when(openingHourValidators.iterator()).thenReturn(Collections.emptyIterator());

        RestaurantResponseDTO result = updateRestaurantUseCase.execute(restaurantId, userId, restaurant);

        assertNotNull(result);
        assertEquals(restaurantId, result.id());
        assertEquals("Restaurante Atualizado", result.name());

        verify(restaurantGateway).existsById(restaurantId);
        verify(restaurantGateway).isRestaurantOwnedByUser(restaurantId, userId);
        verify(restaurantGateway).update(restaurant);
        verify(addressGateway).updateRestaurantAddress(address, restaurantId);
        verify(openingHourGateway).deleteByRestaurantId(restaurantId);
        verify(openingHourGateway).createOpeningHour(openingHour);
        verify(restaurantGateway).findById(restaurantId);
        verify(addressGateway).findByRestaurantId(restaurantId);
        verify(openingHourGateway).findByRestaurantId(restaurantId);
        verify(restaurantMapper).toResponseDTO(updatedRestaurant);
    }

    @Test
    void execute_shouldThrowRestaurantNotFoundException_whenRestaurantDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();

        when(restaurantGateway.existsById(restaurantId)).thenReturn(false);

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> updateRestaurantUseCase.execute(restaurantId, userId, restaurant));

        assertEquals("Restaurante não encontrado", exception.getMessage());
        verify(restaurantGateway).existsById(restaurantId);
        verifyNoMoreInteractions(restaurantGateway);
        verifyNoInteractions(addressGateway, openingHourGateway, restaurantMapper);
    }

    @Test
    void execute_shouldThrowForbiddenOperationException_whenUserNotOwner() {
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();

        when(restaurantGateway.existsById(restaurantId)).thenReturn(true);
        when(restaurantGateway.isRestaurantOwnedByUser(restaurantId, userId)).thenReturn(false);

        ForbiddenOperationException exception = assertThrows(ForbiddenOperationException.class, () -> updateRestaurantUseCase.execute(restaurantId, userId, restaurant));

        assertEquals("Você não tem permissão para atualizar este restaurante.", exception.getMessage());
        verify(restaurantGateway).existsById(restaurantId);
        verify(restaurantGateway).isRestaurantOwnedByUser(restaurantId, userId);
        verifyNoMoreInteractions(restaurantGateway);
        verifyNoInteractions(addressGateway, openingHourGateway, restaurantMapper);
    }

    @Test
    void execute_shouldWork_whenNoOpeningHoursProvided() {
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();
        restaurant.setOpeningHours(Collections.emptyList());

        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setId(restaurantId);
        updatedRestaurant.setOpeningHours(Collections.emptyList());

        RestaurantResponseDTO expectedResponse = new RestaurantResponseDTO(
                restaurantId,
                "Restaurante sem horários",
                null,
                null,
                Collections.emptyList()
        );

        when(restaurantGateway.existsById(restaurantId)).thenReturn(true);
        when(restaurantGateway.isRestaurantOwnedByUser(restaurantId, userId)).thenReturn(true);
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(updatedRestaurant));
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(Collections.emptyList());
        when(restaurantMapper.toResponseDTO(updatedRestaurant)).thenReturn(expectedResponse);

        RestaurantResponseDTO result = updateRestaurantUseCase.execute(restaurantId, userId, restaurant);

        assertNotNull(result);
        assertEquals(restaurantId, result.id());
        assertTrue(result.openingHours().isEmpty());

        verify(restaurantGateway).existsById(restaurantId);
        verify(restaurantGateway).isRestaurantOwnedByUser(restaurantId, userId);
        verify(restaurantGateway).update(restaurant);
        verify(openingHourGateway).deleteByRestaurantId(restaurantId);
        verify(restaurantGateway).findById(restaurantId);
        verify(openingHourGateway).findByRestaurantId(restaurantId);
        verify(restaurantMapper).toResponseDTO(updatedRestaurant);
    }

    @Test
    void execute_shouldValidateOpeningHours() {
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Restaurant restaurant = new Restaurant();

        OpeningHour openingHour = new OpeningHour();
        restaurant.setOpeningHours(List.of(openingHour));

        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setId(restaurantId);
        updatedRestaurant.setOpeningHours(List.of(openingHour));

        OpeningHourValidator validator = mock(OpeningHourValidator.class);
        when(openingHourValidators.iterator()).thenReturn(List.of(validator).iterator());

        when(restaurantGateway.existsById(restaurantId)).thenReturn(true);
        when(restaurantGateway.isRestaurantOwnedByUser(restaurantId, userId)).thenReturn(true);
        when(restaurantGateway.findById(restaurantId)).thenReturn(Optional.of(updatedRestaurant));
        when(openingHourGateway.findByRestaurantId(restaurantId)).thenReturn(List.of(openingHour));
        when(restaurantMapper.toResponseDTO(updatedRestaurant)).thenReturn(mock(RestaurantResponseDTO.class));

        updateRestaurantUseCase.execute(restaurantId, userId, restaurant);

        verify(validator).validate(openingHour);
    }
}