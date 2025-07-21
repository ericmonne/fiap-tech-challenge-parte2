package com.fiap.tech_challenge.parte1.ms_users.infrastructure.gateway.address;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.AddressDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;

import java.util.List;
import java.util.UUID;

public class AddressGatewayImpl implements AddressGateway {

    private final AddressDataSource addressDataSource;

    public AddressGatewayImpl(AddressDataSource addressDataSource) {
        this.addressDataSource = addressDataSource;
    }

    @Override
    public void save(List<Address> addresses, UUID generatedUserId) {
        addressDataSource.save(addresses, generatedUserId);
    }

    @Override
    public void update(List<Address> addresses, UUID id) {
        addressDataSource.update(addresses, id);
    }
}
