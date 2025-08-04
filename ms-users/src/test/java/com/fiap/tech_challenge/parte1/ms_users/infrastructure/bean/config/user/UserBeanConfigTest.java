package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.user;

import com.fiap.tech_challenge.parte1.ms_users.application.controller.UsersControllerInputPortImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller.UsersControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.user.IUserMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.token.TokenProvider;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.Authenticator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.service.PasswordPolicy;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.user.UserGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.AddressMapper;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class UserBeanConfigTest {

    private final UserBeanConfig config = new UserBeanConfig();

    @Test
    void testRegisterUserDataSource() {
        JdbcUserRepository repo = mock(JdbcUserRepository.class);
        UserMapper mapper = new UserMapper(new AddressMapper());

        UserDataSource dataSource = config.registerUserDataSource(repo, mapper);

        assertNotNull(dataSource);
        assertInstanceOf(JdbcUserDataSource.class, dataSource);
    }

    @Test
    void testRegisterUserGateway() {
        UserDataSource dataSource = mock(UserDataSource.class);

        UserGateway gateway = config.registerUserGateway(dataSource);

        assertNotNull(gateway);
        assertInstanceOf(UserGatewayImpl.class, gateway);
    }

    @Test
    void testRegisterUserUseCase() {
        UserGateway userGateway = mock(UserGateway.class);
        AddressGateway addressGateway = mock(AddressGateway.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        TokenProvider tokenProvider = mock(TokenProvider.class);
        IUserMapper userMapper = mock(IUserMapper.class);
        List<UserValidator> validators = List.of();
        UserTypeGateway userTypeGateway = mock(UserTypeGateway.class);

        RegisterUserUseCase useCase = config.registerUserUseCase(userGateway, addressGateway, passwordEncoder, tokenProvider, userMapper, validators, userTypeGateway);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterUpdateUserUseCase() {
        UserGateway userGateway = mock(UserGateway.class);
        AddressGateway addressGateway = mock(AddressGateway.class);

        UpdateUserUseCase useCase = config.registerUpdateUserUseCase(userGateway, addressGateway);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterFindListUserUseCase() {
        UserGateway userGateway = mock(UserGateway.class);
        AddressGateway addressGateway = mock(AddressGateway.class);

        FindListUserUseCase useCase = config.registerFindListUserUseCase(userGateway, addressGateway);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterFindByIdUserUserCase() {
        UserGateway userGateway = mock(UserGateway.class);
        AddressGateway addressGateway = mock(AddressGateway.class);

        FindByIdUserUseCase useCase = config.registerFindByIdUserUserCase(userGateway, addressGateway);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterDeactivateUserUseCase() {
        UserGateway userGateway = mock(UserGateway.class);

        DeactivateUserUseCase useCase = config.registerDeactivateUserUseCase(userGateway);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterReactivateUserUseCase() {
        UserGateway userGateway = mock(UserGateway.class);

        ReactivateUserUseCase useCase = config.registerReactivateUserUseCase(userGateway);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterPasswordPolicy() {
        PasswordPolicy passwordPolicy = config.registerPasswordPolicy();

        assertNotNull(passwordPolicy);
    }

    @Test
    void testRegisterChangePasswordUserUseCase() {
        UserGateway userGateway = mock(UserGateway.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        PasswordPolicy passwordPolicy = config.registerPasswordPolicy();

        ChangePasswordUserUseCase useCase = config.registerChangePasswordUserUseCase(userGateway, passwordEncoder, passwordPolicy);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterAuthenticateUserUseCase() {
        TokenProvider tokenProvider = mock(TokenProvider.class);
        Authenticator authenticator = mock(Authenticator.class);

        AuthenticateUserUseCase useCase = config.registerAuthenticateUserUseCase(tokenProvider, authenticator);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterUsersControllerInputPort() {
        RegisterUserUseCase registerUserUseCase = mock(RegisterUserUseCase.class);
        UpdateUserUseCase updateUserUseCase = mock(UpdateUserUseCase.class);
        FindListUserUseCase findListUserUseCase = mock(FindListUserUseCase.class);
        FindByIdUserUseCase findByIdUserUseCase = mock(FindByIdUserUseCase.class);
        DeactivateUserUseCase deactivateUserUseCase = mock(DeactivateUserUseCase.class);
        ReactivateUserUseCase reactivateUserUseCase = mock(ReactivateUserUseCase.class);
        ChangePasswordUserUseCase changePasswordUserUseCase = mock(ChangePasswordUserUseCase.class);
        AuthenticateUserUseCase authenticateUserUseCase = mock(AuthenticateUserUseCase.class);
        IUserMapper userMapper = mock(IUserMapper.class);

        UsersControllerInputPort controllerInputPort = config.registerUsersControllerInputPort(
                registerUserUseCase,
                updateUserUseCase,
                findListUserUseCase,
                findByIdUserUseCase,
                deactivateUserUseCase,
                reactivateUserUseCase,
                changePasswordUserUseCase,
                authenticateUserUseCase,
                userMapper
        );

        assertNotNull(controllerInputPort);
        assertInstanceOf(UsersControllerInputPortImpl.class, controllerInputPort);
    }
}
