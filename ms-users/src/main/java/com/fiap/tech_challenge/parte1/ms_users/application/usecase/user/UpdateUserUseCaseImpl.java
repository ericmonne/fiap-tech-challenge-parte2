package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.UpdateUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.EmailAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.LoginAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

import java.util.List;
import java.util.UUID;

public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public UpdateUserUseCaseImpl(UserGateway userGateway, AddressGateway addressGateway) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
    }

    @Override
    public User execute(User user) {
        boolean userExists = userGateway.existsById(user.getId());

        if (!userExists) {
            throw new UserNotFoundException("User with id %s not found.".formatted(user.getId()));
        }

        userUpdateValidations(user);

        userGateway.update(user);

        List<Address> addresses = user.getAddress();
        if (addresses != null && !addresses.isEmpty()) {
            addressGateway.updateUserAddress(addresses, user.getId());
        }

        return userGateway.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("User with id %s not found.".formatted(user.getId())));
    }

    private void userUpdateValidations(User user) {
        UUID userId = user.getId();
        String email = user.getEmail();
        String login = user.getLogin();

        if (userGateway.emailAlreadyExistsForDifferentUsers(email, userId)) {
            throw new EmailAlreadyExistsException("O e-mail informado já está em uso.");
        }

        if (userGateway.loginAlreadyExistsForDifferentUsers(login, userId)) {
            throw new LoginAlreadyExistsException("O login informado já está em uso.");
        }
    }

}
