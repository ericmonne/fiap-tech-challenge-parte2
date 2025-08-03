package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.controller.RestaurantControllerInputPortImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.controller.RestaurantControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant.*;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.user.GetUserIdByLoginUseCaseImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.restaurant.RestaurantGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.RestaurantMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RestaurantBeanConfig {

    @Bean
    RestaurantDataSource registerRestaurantDataSource(JdbcRestaurantRepository jdbcRestaurantRepository, RestaurantMapper restaurantMapper) {
        return new JdbcRestaurantDataSource(jdbcRestaurantRepository, restaurantMapper);
    }

    @Bean
    public RestaurantGateway registerRestaurantGateway(RestaurantDataSource restaurantDataSource) {
        return new RestaurantGatewayImpl(restaurantDataSource);
    }

    @Bean
    GetUserIdByLoginUseCase registerGetUserIdByLoginUseCase(UserGateway userGateway) {
        return new GetUserIdByLoginUseCaseImpl(userGateway);
    }

    @Bean
    public RestaurantControllerInputPort registerRestaurantControllerInputPort(FindByIdRestaurantUseCase findByIdRestaurantUseCase, FindAllByUserIdRestaurantUseCase findAllByUserIdRestaurantUseCase, RegisterRestaurantUseCase registerRestaurantUseCase, UpdateRestaurantUseCase updateRestaurantUseCase, DeleteRestaurantUseCase deleteRestaurantUseCase, FindByIdUserUseCase findByIdUserUseCase, GetUserIdByLoginUseCase getUserIdByLoginUseCase, IRestaurantMapper restaurantMapper) {
        return new RestaurantControllerInputPortImpl(findByIdRestaurantUseCase, findAllByUserIdRestaurantUseCase, registerRestaurantUseCase, updateRestaurantUseCase, deleteRestaurantUseCase, findByIdUserUseCase, getUserIdByLoginUseCase, restaurantMapper);
    }

    @Bean
    public DeleteRestaurantUseCase registerDeleteRestaurantUseCase(RestaurantGateway restaurantGateway) {
        return new DeleteRestaurantUseCaseImpl(restaurantGateway);
    }

    @Bean
    public FindAllByUserIdRestaurantUseCase registerFindAllByUserIdRestaurantUseCase(RestaurantGateway restaurantGateway, AddressGateway addressGateway, OpeningHourGateway openingHourGateway, IRestaurantMapper iRestaurantMapper){
        return new FindAllByUserIdRestaurantUseCaseImpl(restaurantGateway, addressGateway, openingHourGateway, iRestaurantMapper);
    }

    @Bean
    public FindByIdRestaurantUseCase registerFindByIdRestaurantUseCase(RestaurantGateway restaurantGateway, AddressGateway addressGateway, OpeningHourGateway openingHourGateway, IRestaurantMapper iRestaurantMapper){
        return new FindByIdRestaurantUseCaseImpl(restaurantGateway, addressGateway, openingHourGateway, iRestaurantMapper);
    }

    @Bean
    public RegisterRestaurantUseCase registerRegisterRestaurantUseCase(RestaurantGateway restaurantGateway, AddressGateway addressGateway, OpeningHourGateway openingHourGateway, IRestaurantMapper restaurantMapper, List<OpeningHourValidator> openingHourValidators){
        return new RegisterRestaurantUseCaseImpl(restaurantGateway, addressGateway, openingHourGateway, restaurantMapper, openingHourValidators);
    }

    @Bean
    public UpdateRestaurantUseCase registerUpdateRestaurantUseCase(RestaurantGateway restaurantGateway, AddressGateway addressGateway, OpeningHourGateway openingHourGateway, IRestaurantMapper restaurantMapper, List<OpeningHourValidator> openingHourValidators){
        return new UpdateRestaurantUseCaseImpl(restaurantGateway, addressGateway, openingHourGateway, restaurantMapper, openingHourValidators);
    }
}
