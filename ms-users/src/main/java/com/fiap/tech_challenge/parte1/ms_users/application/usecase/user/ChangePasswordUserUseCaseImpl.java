package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.ChangePasswordCommand;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.ChangePasswordUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.service.PasswordPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class ChangePasswordUserUseCaseImpl implements ChangePasswordUserUseCase {

    private final UserGateway userGateway;
    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicy passwordPolicy;

    public ChangePasswordUserUseCaseImpl(UserGateway userGateway, PasswordEncoder passwordEncoder, PasswordPolicy passwordPolicy) {
        this.userGateway = userGateway;
        this.passwordEncoder = passwordEncoder;
        this.passwordPolicy = passwordPolicy;
    }

    @Override
    public void execute(UUID id, ChangePasswordCommand command) {
        User user = userGateway
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id %s not found.".formatted(id)));

        boolean oldPasswordMatches = passwordEncoder.matches(command.oldPassword(), user.getPassword());
        boolean isSameAsOld = command.oldPassword().equals(command.newPassword());

        passwordPolicy.validate(oldPasswordMatches, isSameAsOld);
        String newPasswordEncoded = passwordEncoder.encode(command.newPassword());
        userGateway.changePassword(id, newPasswordEncoded);
    }
}
