package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.FindAllByUserIdRestaurantUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;

import java.util.List;
import java.util.UUID;

public class FindAllByUserIdRestaurantUseCaseImpl implements FindAllByUserIdRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final AddressGateway addressGateway;
    private final OpeningHourGateway openingHourGateway;
    private final IRestaurantMapper iRestaurantMapper;

    public FindAllByUserIdRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, AddressGateway addressGateway, OpeningHourGateway openingHourGateway, IRestaurantMapper iRestaurantMapper) {
        this.restaurantGateway = restaurantGateway;
        this.addressGateway = addressGateway;
        this.openingHourGateway = openingHourGateway;
        this.iRestaurantMapper = iRestaurantMapper;
    }

    @Override
    public List<RestaurantResponseDTO> execute(UUID userId, int size, int offset) {
        List<Restaurant> restaurantList = restaurantGateway.findAllRestaurantsByUserId(userId, size, offset);

        for (Restaurant restaurant : restaurantList) {
            addressGateway.findByRestaurantId(restaurant.getId())
                    .ifPresent(restaurant::setAddress);
            List<OpeningHour> openingHours = openingHourGateway.findByRestaurantId(restaurant.getId());
            restaurant.setOpeningHours(openingHours);
        }

        return restaurantList.stream()
                .map(iRestaurantMapper::toResponseDTO)
                .toList();
    }
}
