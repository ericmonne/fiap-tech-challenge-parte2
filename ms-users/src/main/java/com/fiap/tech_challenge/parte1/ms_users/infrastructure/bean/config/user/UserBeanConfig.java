package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.RegisterUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.UpdateUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.UserDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.RegisterUserUseCaseImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.UpdateUserUseCaseImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.gateway.user.UserGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public RegisterUserUseCase registerStudentUseCase(UserGateway userGateway, AddressGateway addressGateway, PasswordEncoder passwordEncoder) {
        return new RegisterUserUseCaseImpl(userGateway, addressGateway, passwordEncoder);
    }

    @Bean
    public UpdateUserUseCase registerUpdateUserUseCase(UserGateway userGateway, AddressGateway addressGateway) {
        return new UpdateUserUseCaseImpl(userGateway, addressGateway);
    }

}

