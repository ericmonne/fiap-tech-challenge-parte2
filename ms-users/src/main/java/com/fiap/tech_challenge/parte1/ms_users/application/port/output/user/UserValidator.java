package com.fiap.tech_challenge.parte1.ms_users.application.port.output.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UsersRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

/**
 * Interface for validating user data in a {@link UsersRequestDTO}.
 * <p>
 * Implementations of this interface should define specific validation rules
 * for user registration or update requests.
 */
public interface UserValidator {
    void validate(User user);
}
