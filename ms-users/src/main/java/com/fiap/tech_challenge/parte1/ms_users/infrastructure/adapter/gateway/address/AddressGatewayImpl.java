package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.address;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class AddressGatewayImpl implements AddressGateway {

    private final AddressDataSource addressDataSource;

    public AddressGatewayImpl(AddressDataSource addressDataSource) {
        this.addressDataSource = addressDataSource;
    }

    @Override
    public void saveUserAddress(List<Address> addresses, UUID generatedUserId) {
        addressDataSource.saveUserAddress(addresses, generatedUserId);
    }

    @Override
    public void updateUserAddress(List<Address> addresses, UUID id) {
        addressDataSource.updateUserAddress(addresses, id);
    }

    @Override
    public List<Address> findAllByUserIds(Set<UUID> userIdSet) {
        return addressDataSource.findAllByUserIds(userIdSet);
    }

    @Override
    public List<Address> findAllByUserId(UUID id) {
        return addressDataSource.findAllByUserIds(Set.of(id));
    }

    @Override
    public Optional<Address> findByRestaurantId(UUID id) {
        return addressDataSource.findByRestaurantId(id);
    }

    @Override
    public void saveUserAddress(Address address, UUID restaurantId) {
        addressDataSource.saveRestaurantAddress(address, restaurantId);
    }
}
