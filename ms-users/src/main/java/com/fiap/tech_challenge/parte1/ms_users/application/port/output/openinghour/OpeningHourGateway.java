package com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.OpeningHour;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OpeningHourGateway {
    UUID createOpeningHour(OpeningHour openingHour);
    Optional<OpeningHour> findById(UUID openingHourId);
    void update(OpeningHour openingHour);
    List<OpeningHour> findByRestaurantId(UUID restaurantId);
    void deleteByRestaurantId(UUID id);
}
