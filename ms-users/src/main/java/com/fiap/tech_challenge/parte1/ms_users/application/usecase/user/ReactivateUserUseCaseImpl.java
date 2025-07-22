package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.ReactivateUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

import java.util.UUID;

public class ReactivateUserUseCaseImpl implements ReactivateUserUseCase {

    private final UserGateway userGateway;

    public ReactivateUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void execute(UUID id) {
        User user = userGateway
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id %s not found.".formatted(id).replace(" ", "")));
        userGateway.reactivate(user.getId());
    }
}
