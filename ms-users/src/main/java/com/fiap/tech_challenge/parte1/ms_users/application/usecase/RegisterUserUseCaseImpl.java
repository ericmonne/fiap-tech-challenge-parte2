package com.fiap.tech_challenge.parte1.ms_users.application.usecase;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.RegisterUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCaseImpl(UserGateway userGateway, AddressGateway addressGateway, PasswordEncoder passwordEncoder) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User execute(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UUID userId = userGateway.createUser(user);
        List<Address> addresses = user.getAddresses();
        addressGateway.save(addresses, userId);
        return userGateway.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id %s not found.".formatted(userId)));
    }
}
