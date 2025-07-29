package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.CreateUserDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.RegisterUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.user.IUserMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.token.TokenProvider;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserValidator;
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
    private final List<UserValidator> userValidators;

    public RegisterUserUseCaseImpl(UserGateway userGateway, AddressGateway addressGateway, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, IUserMapper iUserMapper, List<UserValidator> userValidators) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.iUserMapper = iUserMapper;
        this.userValidators = userValidators;
    }

    @Override
    public CreateUserDTO execute(User user) {
        validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UUID userId = userGateway.createUser(user);
        List<Address> addresses = user.getAddresses();
        addressGateway.saveUserAddress(addresses, userId);
        User userEntityAfterCreation = userGateway.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id %s not found.".formatted(userId)));
        return new CreateUserDTO(iUserMapper.toResponseDTO(userEntityAfterCreation), tokenProvider.generateToken(user.getLogin()));
    }

    private void validateUser(User user) {
        for (UserValidator userValidator : userValidators) {
            userValidator.validate(user);
        }
    }
}
