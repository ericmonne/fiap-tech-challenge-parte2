package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.FindByIdUserUserCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

import java.util.List;
import java.util.UUID;

public class FindByIdUserUserCaseImpl implements FindByIdUserUserCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public FindByIdUserUserCaseImpl(UserGateway userGateway, AddressGateway addressGateway) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
    }

    @Override
    public User execute(UUID id) {
        User user = userGateway
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("Usuário com id %s não encontrado.", id)));
        List<Address> addressList = addressGateway.findAllByUserId(id);
        user.setAddress(addressList);
        return user;
    }
}
