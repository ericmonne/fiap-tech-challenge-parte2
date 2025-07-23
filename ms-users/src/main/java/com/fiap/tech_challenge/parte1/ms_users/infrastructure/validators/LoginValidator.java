package com.fiap.tech_challenge.parte1.ms_users.infrastructure.validators;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.LoginAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.springframework.stereotype.Component;


@Component
public class LoginValidator implements UserValidator {

    private final UserGateway userGateway;

    public LoginValidator(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void validate(User user) {
        if (userGateway.existsByLogin(user.getLogin())) {
            throw new LoginAlreadyExistsException("The provided login is already in use.");
        }
    }
}
