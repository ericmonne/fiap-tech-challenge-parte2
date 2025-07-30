package com.fiap.tech_challenge.parte1.ms_users.application.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.controller.RestaurantControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.FindByIdUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class RestaurantControllerInputPortImpl implements RestaurantControllerInputPort {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantControllerInputPortImpl.class);

    private final FindByIdRestaurantUseCase findByIdRestaurantUseCase;
    private final FindAllByUserIdRestaurantUseCase findAllByUserIdRestaurantUseCase;
    private final RegisterRestaurantUseCase registerRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;
    private final DeleteRestaurantUseCase deleteRestaurantUseCase;
    private final FindByIdUserUseCase findByIdUserUseCase;
    private final IRestaurantMapper restaurantMapper;

    public RestaurantControllerInputPortImpl(FindByIdRestaurantUseCase findByIdRestaurantUseCase, FindAllByUserIdRestaurantUseCase findAllByUserIdRestaurantUseCase, RegisterRestaurantUseCase registerRestaurantUseCase, UpdateRestaurantUseCase updateRestaurantUseCase, DeleteRestaurantUseCase deleteRestaurantUseCase, FindByIdUserUseCase findByIdUserUseCase, IRestaurantMapper restaurantMapper) {
        this.findByIdRestaurantUseCase = findByIdRestaurantUseCase;
        this.findAllByUserIdRestaurantUseCase = findAllByUserIdRestaurantUseCase;
        this.registerRestaurantUseCase = registerRestaurantUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
        this.findByIdUserUseCase = findByIdUserUseCase;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public RestaurantResponseDTO getRestaurantById(UUID restaurantId) {
        return findByIdRestaurantUseCase.execute(restaurantId);
    }

    @Override
    public List<RestaurantResponseDTO> findAllRestaurantsByUser(UUID userId, int size, int page) {
        return findAllByUserIdRestaurantUseCase.execute(userId, size, page);
    }

    @Override
    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto, UUID userId) {
        User user = findByIdUserUseCase.execute(userId);
        Restaurant restaurant = restaurantMapper.toEntity(dto, user);
        return registerRestaurantUseCase.execute(restaurant);
    }

    @Override
    public RestaurantResponseDTO updateRestaurant(UUID restaurantId, RestaurantRequestDTO dto, UUID userId) {
        User user = findByIdUserUseCase.execute(userId);
        Restaurant restaurant = restaurantMapper.toEntity(dto, user);
        return updateRestaurantUseCase.execute(restaurantId, userId, restaurant);
    }

    @Override
    public void deleteRestaurant(UUID restaurantId, UUID userId) {
        User user = findByIdUserUseCase.execute(userId);
        deleteRestaurantUseCase.execute(restaurantId, user.getId());
    }
}
