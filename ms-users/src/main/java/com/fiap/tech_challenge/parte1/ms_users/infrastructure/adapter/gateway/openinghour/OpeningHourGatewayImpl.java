package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OpeningHourGatewayImpl implements OpeningHourGateway {

    private final OpeningHourDataSource openingHourDataSource;

    public OpeningHourGatewayImpl(OpeningHourDataSource openingHourDataSource) {
        this.openingHourDataSource = openingHourDataSource;
    }

    @Override
    public UUID createOpeningHour(OpeningHour openingHour) {
        return this.openingHourDataSource.createOpeningHour(openingHour);
    }

    @Override
    public Optional<OpeningHour> findById(UUID openingHourId) {
        return this.openingHourDataSource.findById(openingHourId);
    }

    @Override
    public void update(OpeningHour openingHour) {
        this.openingHourDataSource.update(openingHour);
    }

    @Override
    public List<OpeningHour> findByRestaurantId(UUID restaurantId) {
        return this.openingHourDataSource.findByRestaurantId(restaurantId);
    }

    @Override
    public void deleteByRestaurantId(UUID restaurantId) {
        this.openingHourDataSource.deleteByRestaurantId(restaurantId);
    }
}
