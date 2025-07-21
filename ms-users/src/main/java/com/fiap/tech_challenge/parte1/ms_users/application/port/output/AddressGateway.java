package com.fiap.tech_challenge.parte1.ms_users.application.port.output;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;

import java.util.List;
import java.util.UUID;

public interface AddressGateway {
    void save(List<Address> addresses, UUID generatedUserId);

    void update(List<Address> addresses, UUID id);
}
