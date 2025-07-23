package com.fiap.tech_challenge.parte1.ms_users.infrastructure.validators;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.UsersRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.EmailAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator implements UserValidator {

    private final UserGateway userGateway;

    public EmailValidator(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void validate(User user) {
        if (userGateway.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("The provided email is already in use.");
        }
    }
}
