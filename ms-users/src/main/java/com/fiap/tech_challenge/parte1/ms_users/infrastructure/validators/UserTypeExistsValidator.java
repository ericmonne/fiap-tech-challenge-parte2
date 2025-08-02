package com.fiap.tech_challenge.parte1.ms_users.infrastructure.validators;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.EmailAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserTypeDoesNotExistException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.springframework.stereotype.Component;

@Component
public class UserTypeExistsValidator implements UserValidator {

    private final UserTypeGateway userTypeGateway;

    public UserTypeExistsValidator(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public void validate(User user) {

        UserType userType = user.getUserType();

        if (userType == null || !userTypeGateway.existsByName(userType.getName())) {
            throw new UserTypeDoesNotExistException("The provided user type does not exist. Please try again with a valid user type name.");
        }
    }
}
