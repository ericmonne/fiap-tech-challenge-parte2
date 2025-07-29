package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.FindByIdRestaurantUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.RestaurantNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;

import java.util.List;
import java.util.UUID;


public class FindByIdRestaurantUseCaseImpl implements FindByIdRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final AddressGateway addressGateway;
    private final OpeningHourGateway openingHourGateway;
    private final IRestaurantMapper iRestaurantMapper;

    public FindByIdRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, AddressGateway addressGateway, OpeningHourGateway openingHourGateway, IRestaurantMapper iRestaurantMapper) {
        this.restaurantGateway = restaurantGateway;
        this.addressGateway = addressGateway;
        this.openingHourGateway = openingHourGateway;
        this.iRestaurantMapper = iRestaurantMapper;
    }

    @Override
    public RestaurantResponseDTO execute(UUID restaurantId) {
        Restaurant restaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado: " + restaurantId));

        addressGateway.findByRestaurantId(restaurantId)
                .ifPresent(restaurant::setAddress);

        List<OpeningHour> openingHour = openingHourGateway.findByRestaurantId(restaurantId);
        restaurant.setOpeningHours(openingHour);

        return iRestaurantMapper.toResponseDTO(restaurant);
    }
}
