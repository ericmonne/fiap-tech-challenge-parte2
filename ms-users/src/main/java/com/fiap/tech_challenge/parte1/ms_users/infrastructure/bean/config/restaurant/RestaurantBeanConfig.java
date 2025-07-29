package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.controller.RestaurantControllerInputPortImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.controller.UsersControllerInputPortImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller.UsersControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.user.IUserMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.restaurant.RestaurantGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.user.UserGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.RestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.UserMapper;
import org.springframework.context.annotation.Bean;

public class RestaurantBeanConfig {

    @Bean
    RestaurantDataSource registerRestaurantDataSource(JdbcRestaurantRepository jdbcRestaurantRepository, RestaurantMapper restaurantMapper) {
        return new JdbcRestaurantDataSource(jdbcRestaurantRepository, restaurantMapper);
    }

    @Bean
    public RestaurantGateway registerRestaurantGateway(RestaurantDataSource restaurantDataSource) {
        return new RestaurantGatewayImpl(restaurantDataSource);
    }

//    @Bean
//    public RestaurantControllerInputPort registerRestaurantControllerInputPort(RegisterUserUseCase registerStudentUseCase, UpdateUserUseCase registerUpdateUserUseCase, FindListUserUseCase registerFindListUserUseCase, FindByIdUserUseCase registerFindByIdUserUserCase, DeactivateUserUseCase registerDeactivateUserUseCase, ReactivateUserUseCase registerReactivateUserUseCase, ChangePasswordUserUseCase registerChangePasswordUserUseCase, AuthenticateUserUseCase registerAuthenticateUserUseCase, IUserMapper iUserMapper) {
//        return new RestaurantControllerInputPortImpl(registerStudentUseCase, registerUpdateUserUseCase, registerFindListUserUseCase, registerFindByIdUserUserCase, registerDeactivateUserUseCase, registerReactivateUserUseCase, registerChangePasswordUserUseCase, registerAuthenticateUserUseCase, iUserMapper);
//    }

    //UseCases
}
