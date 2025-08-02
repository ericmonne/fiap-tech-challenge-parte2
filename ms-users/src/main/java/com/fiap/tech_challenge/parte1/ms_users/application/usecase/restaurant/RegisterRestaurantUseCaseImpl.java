package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.RegisterRestaurantUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.RestaurantNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;

import java.util.List;
import java.util.UUID;

public class RegisterRestaurantUseCaseImpl implements RegisterRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final AddressGateway addressGateway;
    private final OpeningHourGateway openingHourGateway;
    private final IRestaurantMapper restaurantMapper;
    private final List<OpeningHourValidator> openingHourValidators;

    public RegisterRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, AddressGateway addressGateway, OpeningHourGateway openingHourGateway, IRestaurantMapper restaurantMapper, List<OpeningHourValidator> openingHourValidators) {
        this.restaurantGateway = restaurantGateway;
        this.addressGateway = addressGateway;
        this.openingHourGateway = openingHourGateway;
        this.restaurantMapper = restaurantMapper;
        this.openingHourValidators = openingHourValidators;
    }

    @Override
    public RestaurantResponseDTO execute(Restaurant restaurant) {
        UUID restaurantId = restaurantGateway.createRestaurant(restaurant);
        restaurant.setId(restaurantId);

        saveAddress(restaurant);
        saveOpeningHours(restaurant);

        Restaurant savedRestaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Erro ao salvar restaurante"));

        Address savedAddress = addressGateway.findByRestaurantId(savedRestaurant.getId())
                .orElse(null);
        savedRestaurant.setAddress(savedAddress);

        List<OpeningHour> openingHourList = openingHourGateway.findByRestaurantId(savedRestaurant.getId())
                        .stream().toList();
        savedRestaurant.setOpeningHours(openingHourList);

        return restaurantMapper.toResponseDTO(savedRestaurant);
    }

    private void saveAddress(Restaurant restaurant) {
        addressGateway.saveRestaurantAddress(restaurant.getAddress(), restaurant.getId());
    }

    private void saveOpeningHours(Restaurant restaurant) {
        List<OpeningHour> openingHourList = restaurant.getOpeningHours();
        for (OpeningHour openingHour : openingHourList) {
            openingHour.setRestaurant(restaurant);
            for (OpeningHourValidator validator : openingHourValidators) {
                validator.validate(openingHour);
            }
            UUID openingHourId = openingHourGateway.createOpeningHour(openingHour);
            openingHour.setId(openingHourId);
        }
    }
}
