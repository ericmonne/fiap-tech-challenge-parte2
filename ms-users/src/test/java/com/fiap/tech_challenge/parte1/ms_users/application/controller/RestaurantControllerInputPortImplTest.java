package com.fiap.tech_challenge.parte1.ms_users.application.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.FindByIdUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.GetUserIdByLoginUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerInputPortImplTest {

    @Mock
    private FindByIdRestaurantUseCase findByIdRestaurantUseCase;
    @Mock
    private FindAllByUserIdRestaurantUseCase findAllByUserIdRestaurantUseCase;
    @Mock
    private RegisterRestaurantUseCase registerRestaurantUseCase;
    @Mock
    private UpdateRestaurantUseCase updateRestaurantUseCase;
    @Mock
    private DeleteRestaurantUseCase deleteRestaurantUseCase;
    @Mock
    private FindByIdUserUseCase findByIdUserUseCase;
    @Mock
    private GetUserIdByLoginUseCase getUserIdByLoginUseCase;
    @Mock
    private IRestaurantMapper restaurantMapper;

    private RestaurantControllerInputPortImpl controller;

    @BeforeEach
    void setup() {
        controller = new RestaurantControllerInputPortImpl(
                findByIdRestaurantUseCase,
                findAllByUserIdRestaurantUseCase,
                registerRestaurantUseCase,
                updateRestaurantUseCase,
                deleteRestaurantUseCase,
                findByIdUserUseCase,
                getUserIdByLoginUseCase,
                restaurantMapper
        );
    }

    @Test
    void shouldReturnRestaurantById() {
        UUID id = UUID.randomUUID();
        RestaurantResponseDTO expected = Mockito.mock(RestaurantResponseDTO.class);

        when(findByIdRestaurantUseCase.execute(id)).thenReturn(expected);

        RestaurantResponseDTO result = controller.getRestaurantById(id);

        assertEquals(expected, result);
        verify(findByIdRestaurantUseCase).execute(id);
    }

    @Test
    void shouldReturnAllRestaurantsByUser() {
        UUID userId = UUID.randomUUID();
        int size = 5, page = 0;
        List<RestaurantResponseDTO> expectedList = List.of(mock(RestaurantResponseDTO.class));

        when(findAllByUserIdRestaurantUseCase.execute(userId, size, page)).thenReturn(expectedList);

        List<RestaurantResponseDTO> result = controller.findAllRestaurantsByUser(userId, size, page);

        assertEquals(expectedList, result);
        verify(findAllByUserIdRestaurantUseCase).execute(userId, size, page);
    }

    @Test
    void shouldCreateRestaurant() {
        UUID userId = UUID.randomUUID();
        RestaurantRequestDTO dto = mock(RestaurantRequestDTO.class);
        User user = mock(User.class);
        Restaurant restaurant = mock(Restaurant.class);
        RestaurantResponseDTO responseDTO = mock(RestaurantResponseDTO.class);

        when(findByIdUserUseCase.execute(userId)).thenReturn(user);
        when(restaurantMapper.toEntity(dto, user)).thenReturn(restaurant);
        when(registerRestaurantUseCase.execute(restaurant)).thenReturn(responseDTO);

        RestaurantResponseDTO result = controller.createRestaurant(dto, userId);

        assertEquals(responseDTO, result);
        verify(findByIdUserUseCase).execute(userId);
        verify(restaurantMapper).toEntity(dto, user);
        verify(registerRestaurantUseCase).execute(restaurant);
    }

    @Test
    void shouldUpdateRestaurant() {
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        RestaurantRequestDTO dto = mock(RestaurantRequestDTO.class);
        User user = mock(User.class);
        Restaurant restaurant = mock(Restaurant.class);
        RestaurantResponseDTO responseDTO = mock(RestaurantResponseDTO.class);

        when(findByIdUserUseCase.execute(userId)).thenReturn(user);
        when(restaurantMapper.toEntity(dto, user)).thenReturn(restaurant);
        when(updateRestaurantUseCase.execute(restaurantId, userId, restaurant)).thenReturn(responseDTO);

        RestaurantResponseDTO result = controller.updateRestaurant(restaurantId, dto, userId);

        assertEquals(responseDTO, result);
        verify(findByIdUserUseCase).execute(userId);
        verify(restaurantMapper).toEntity(dto, user);
        verify(updateRestaurantUseCase).execute(restaurantId, userId, restaurant);
    }

    @Test
    void shouldDeleteRestaurant() {
        UUID restaurantId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        User user = mock(User.class);

        when(findByIdUserUseCase.execute(userId)).thenReturn(user);
        when(user.getId()).thenReturn(userId);

        controller.deleteRestaurant(restaurantId, userId);

        verify(findByIdUserUseCase).execute(userId);
        verify(deleteRestaurantUseCase).execute(restaurantId, userId);
    }

    @Test
    void shouldReturnUserIdByLogin() {
        String login = "ana@teste.com";
        UUID expectedId = UUID.randomUUID();

        when(getUserIdByLoginUseCase.getUserIdByLogin(login)).thenReturn(expectedId);

        UUID result = controller.getUserIdByLogin(login);

        assertEquals(expectedId, result);
        verify(getUserIdByLoginUseCase).getUserIdByLogin(login);
    }
}