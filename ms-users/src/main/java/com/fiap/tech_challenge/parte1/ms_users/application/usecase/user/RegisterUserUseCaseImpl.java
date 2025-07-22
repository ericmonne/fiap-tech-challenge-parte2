package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.CreateUserDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.RegisterUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.IUserMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.token.TokenProvider;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
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
    private final TokenProvider tokenProvider;
    private final IUserMapper iUserMapper;

    public RegisterUserUseCaseImpl(UserGateway userGateway, AddressGateway addressGateway, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, IUserMapper iUserMapper) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.iUserMapper = iUserMapper;
    }

    @Override
    public CreateUserDTO execute(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UUID userId = userGateway.createUser(user);
        List<Address> addresses = user.getAddresses();
        addressGateway.save(addresses, userId);
        User userEntityAfterCreation = userGateway.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id %s not found.".formatted(userId)));
        return new CreateUserDTO(iUserMapper.toResponseDTO(userEntityAfterCreation), tokenProvider.generateToken(user.getLogin()));
    }
}
