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
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.user.*;
import com.fiap.tech_challenge.parte1.ms_users.domain.service.PasswordPolicy;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.user.UserGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class UserBeanConfig {

    @Bean
    UserDataSource registerUserDataSource(JdbcUserRepository jdbcUserRepository, UserMapper userMapper) {
        return new JdbcUserDataSource(jdbcUserRepository, userMapper);
    }

    @Bean
    public UserGateway registerUserGateway(UserDataSource userDataSource) {
        return new UserGatewayImpl(userDataSource);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserGateway userGateway, AddressGateway addressGateway, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, IUserMapper iUserMapper, List<UserValidator> userValidators, UserTypeGateway userTypeGateway) {
        return new RegisterUserUseCaseImpl(userGateway, userTypeGateway, addressGateway, passwordEncoder, tokenProvider, iUserMapper, userValidators);
    }

    @Bean
    public UpdateUserUseCase registerUpdateUserUseCase(UserGateway userGateway, AddressGateway addressGateway) {
        return new UpdateUserUseCaseImpl(userGateway, addressGateway);
    }

    @Bean
    public FindListUserUseCase registerFindListUserUseCase(UserGateway userGateway, AddressGateway addressGateway) {
        return new FindListUserUseCaseImpl(userGateway, addressGateway);
    }

    @Bean
    public FindByIdUserUseCase registerFindByIdUserUserCase(UserGateway userGateway, AddressGateway addressGateway) {
        return new FindByIdUserUseCaseImpl(userGateway, addressGateway);
    }

    @Bean
    public DeactivateUserUseCase registerDeactivateUserUseCase(UserGateway userGateway) {
        return new DeactivateUserUseCaseImpl(userGateway);
    }

    @Bean
    public ReactivateUserUseCase registerReactivateUserUseCase(UserGateway userGateway) {
        return new ReactivateUserUseCaseImpl(userGateway);
    }

    @Bean
    public PasswordPolicy registerPasswordPolicy() {
        return new PasswordPolicy();
    }

    @Bean
    public ChangePasswordUserUseCase registerChangePasswordUserUseCase(UserGateway userGateway, PasswordEncoder passwordEncoder, PasswordPolicy passwordPolicy) {
        return new ChangePasswordUserUseCaseImpl(userGateway, passwordEncoder, passwordPolicy);
    }

    @Bean
    public AuthenticateUserUseCase registerAuthenticateUserUseCase(TokenProvider tokenProvider, Authenticator authenticator) {
        return new AuthenticateUserUseCaseImpl(tokenProvider, authenticator);
    }

    @Bean
    public UsersControllerInputPort registerUsersControllerInputPort(RegisterUserUseCase registerStudentUseCase, UpdateUserUseCase registerUpdateUserUseCase, FindListUserUseCase registerFindListUserUseCase, FindByIdUserUseCase registerFindByIdUserUserCase, DeactivateUserUseCase registerDeactivateUserUseCase, ReactivateUserUseCase registerReactivateUserUseCase, ChangePasswordUserUseCase registerChangePasswordUserUseCase, AuthenticateUserUseCase registerAuthenticateUserUseCase, IUserMapper iUserMapper) {
        return new UsersControllerInputPortImpl(registerStudentUseCase, registerUpdateUserUseCase, registerFindListUserUseCase, registerFindByIdUserUserCase, registerDeactivateUserUseCase, registerReactivateUserUseCase, registerChangePasswordUserUseCase, registerAuthenticateUserUseCase, iUserMapper);
    }

}

