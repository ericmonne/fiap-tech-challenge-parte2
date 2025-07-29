package com.fiap.tech_challenge.parte1.ms_users.application.port.output.address;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AddressGateway {
    void saveUserAddress(List<Address> addresses, UUID generatedUserId);

    void updateUserAddress(List<Address> addresses, UUID id);

    List<Address> findAllByUserIds(Set<UUID> userIdSet);

    List<Address> findAllByUserId(UUID id);

    Optional<Address> findByRestaurantId(UUID id);

    void saveRestaurantAddress(Address address, UUID restaurantId);

}
