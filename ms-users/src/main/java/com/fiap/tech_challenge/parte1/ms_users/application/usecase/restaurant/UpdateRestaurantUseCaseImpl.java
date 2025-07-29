package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.UpdateRestaurantUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.RestaurantNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UpdateRestaurantUseCaseImpl implements UpdateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;
    private final AddressGateway addressGateway;
    private final OpeningHourGateway openingHourGateway;
    private final IRestaurantMapper restaurantMapper;
    private final List<OpeningHourValidator> openingHourValidators;

    public UpdateRestaurantUseCaseImpl(RestaurantGateway restaurantGateway, AddressGateway addressGateway, OpeningHourGateway openingHourGateway, IRestaurantMapper restaurantMapper, List<OpeningHourValidator> openingHourValidators) {
        this.restaurantGateway = restaurantGateway;
        this.addressGateway = addressGateway;
        this.openingHourGateway = openingHourGateway;
        this.restaurantMapper = restaurantMapper;
        this.openingHourValidators = openingHourValidators;
    }

    @Transactional
    @Override
    public RestaurantResponseDTO execute(Restaurant restaurant) {
        if (!restaurantGateway.existsById(restaurant.getId())) {
            throw new RestaurantNotFoundException("Restaurante não encontrado");
        }

        restaurantGateway.update(restaurant);
        addressGateway.updateRestaurantAddress(restaurant.getAddress(), restaurant.getId());
        updateOpeningHours(restaurant);

        Restaurant updated = restaurantGateway.findById(restaurant.getId())
                .orElseThrow(() -> new RestaurantNotFoundException("Erro ao buscar restaurante atualizado"));

        return restaurantMapper.toResponseDTO(updated);
    }

    private void updateOpeningHours(Restaurant restaurant) {
        List<OpeningHour> openingHours = restaurant.getOpeningHours();
        for (OpeningHour oh : openingHours) {
            oh.setRestaurant(restaurant);
            for (OpeningHourValidator validator : openingHourValidators) {
                validator.validate(oh);
            }
        }

        openingHourGateway.deleteByRestaurantId(restaurant.getId());

        for (OpeningHour oh : openingHours) {
            openingHourGateway.createOpeningHour(oh);
        }
    }
}
