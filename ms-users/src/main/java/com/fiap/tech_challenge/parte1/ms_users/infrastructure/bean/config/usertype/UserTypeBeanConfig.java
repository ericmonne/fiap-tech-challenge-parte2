package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.controller.UserTypeControllerInputPortImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.UserTypeControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype.*;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.usertype.UserTypeGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype.JdbcUserTypeDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype.JdbcUserTypeRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.UserTypeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeBeanConfig {

    @Bean
    UserTypeDataSource registercreateUserTypeDataSource(
            final JdbcUserTypeRepository jdbcUserTypeRepository,
            final UserTypeMapper userTypeMapper
    ) {
        return new JdbcUserTypeDataSource(jdbcUserTypeRepository, userTypeMapper);
    }

    @Bean
    public UserTypeGateway registercreateUserTypeGatawey(
            final UserTypeDataSource userTypeDataSource
    ) {
        return new UserTypeGatewayImpl(userTypeDataSource);
    }

    @Bean
    public CreateUserTypeUseCase registerCreateUserTypeUseCase(
            final UserTypeGateway userTypeGateway,
            final IUserTypeMapper iUserTypeMapper
    ) {
        return new CreateUserTypeUseCaseImpl(userTypeGateway, iUserTypeMapper);
    }

    @Bean
    public UpdateUserTypeUseCase registerUpdaUserTypeUseCase(
            final UserTypeGateway userTypeGateway
    ) {
        return new UpdateUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public FindListUserTypeUseCase registerFindListUserTypeUseCase(
            final UserTypeGateway userTypeGateway
    ) {
        return new FindListUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public FindByIdUserTypeUseCase registerFindByIdUserTypeUserCase(
            final UserTypeGateway userTypeGateway
    ) {
        return new FindByIdUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public DeactivateUserTypeUseCase registerDeactivateUserTypeUseCase(
            final UserTypeGateway userTypeGateway
    ) {
        return new DeactivateUserTypeUseCaseImpl(userTypeGateway);
    }

    @Bean
    public ReactivateUserTypeUserCase registerReactivateUserTypeUseCase(
            final UserTypeGateway userTypeGateway
    ) {
        return new ReactivateUserTypeImpl(userTypeGateway);
    }

    @Bean
    public UserTypeControllerInputPort registerUserTypeControllerInputPort(
            final CreateUserTypeUseCase registerCreateUserTypeUseCase,
            final UpdateUserTypeUseCase registerUpdaUserTypeUseCase,
            final FindListUserTypeUseCase registerFindListUserTypeUseCase,
            final FindByIdUserTypeUseCase registerFindByIdUserTypeUserCase,
            final DeactivateUserTypeUseCase registerDeactivateUserTypeUseCase,
            final ReactivateUserTypeUserCase registerReactivateUserTypeUseCase,
            final IUserTypeMapper iUserTypeMapper
    ) {
        return new UserTypeControllerInputPortImpl(
                registerCreateUserTypeUseCase,
                registerUpdaUserTypeUseCase,
                registerFindByIdUserTypeUserCase,
                registerFindListUserTypeUseCase,
                registerDeactivateUserTypeUseCase,
                registerReactivateUserTypeUseCase,
                iUserTypeMapper
        );
    }
}
