package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.GetUserIdByLoginUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserIdByLoginUseCaseImpl implements GetUserIdByLoginUseCase {

    private final UserGateway userGateway;

    public GetUserIdByLoginUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public UUID getUserIdByLogin(String login) {
        User user = userGateway.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User not found with login: " + login));
        return user.getId();
    }
}