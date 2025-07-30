package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.CreateUserTypeUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype.CreateUserTypeUseCaseImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.usertype.UserTypeGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype.JdbcUserTypeDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype.JdbcUserTypeRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.UserTypeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeBeanConfig {

    @Bean
    UserTypeDataSource createUserTypeDataSource(JdbcUserTypeRepository jdbcUserTypeRepository, UserTypeMapper userTypeMapper){
        return new JdbcUserTypeDataSource(jdbcUserTypeRepository, userTypeMapper);
    }

    @Bean
    public UserTypeGateway createUserTypeGatawey(UserTypeDataSource userTypeDataSource){
        return new UserTypeGatewayImpl(userTypeDataSource);
    }

    @Bean
    public CreateUserTypeUseCase createUserTypeUseCase(UserTypeGateway userTypeGateway, UserTypeMapper userTypeMapper){
        return new CreateUserTypeUseCaseImpl(userTypeGateway, userTypeMapper);
    }
}
