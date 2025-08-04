package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.controller.RestaurantControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.FindByIdUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.GetUserIdByLoginUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.restaurant.RestaurantGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.AddressMapper;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.OpeningHourMapper;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.RestaurantMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class RestaurantBeanConfigTest {

    private final RestaurantBeanConfig config = new RestaurantBeanConfig();

    @Test
    void testRegisterRestaurantDataSource() {
        JdbcRestaurantRepository repo = mock(JdbcRestaurantRepository.class);
        RestaurantMapper mapper = new RestaurantMapper(new AddressMapper(), new OpeningHourMapper());

        RestaurantDataSource dataSource = config.registerRestaurantDataSource(repo, mapper);

        assertNotNull(dataSource);
        assertInstanceOf(JdbcRestaurantDataSource.class, dataSource);
    }

    @Test
    void testRegisterRestaurantGateway() {
        RestaurantDataSource dataSource = mock(RestaurantDataSource.class);

        RestaurantGateway gateway = config.registerRestaurantGateway(dataSource);

        assertNotNull(gateway);
        assertInstanceOf(RestaurantGatewayImpl.class, gateway);
    }

    @Test
    void testRegisterGetUserIdByLoginUseCase() {
        UserGateway userGateway = mock(UserGateway.class);

        GetUserIdByLoginUseCase useCase = config.registerGetUserIdByLoginUseCase(userGateway);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterRestaurantControllerInputPort() {
        FindByIdRestaurantUseCase findById = mock(FindByIdRestaurantUseCase.class);
        FindAllByUserIdRestaurantUseCase findAllByUserId = mock(FindAllByUserIdRestaurantUseCase.class);
        RegisterRestaurantUseCase registerUseCase = mock(RegisterRestaurantUseCase.class);
        UpdateRestaurantUseCase updateUseCase = mock(UpdateRestaurantUseCase.class);
        DeleteRestaurantUseCase deleteUseCase = mock(DeleteRestaurantUseCase.class);
        FindByIdUserUseCase findByIdUserUseCase = mock(FindByIdUserUseCase.class);
        GetUserIdByLoginUseCase getUserIdByLoginUseCase = mock(GetUserIdByLoginUseCase.class);
        RestaurantMapper mapper = new RestaurantMapper(new AddressMapper(), new OpeningHourMapper());

        RestaurantControllerInputPort controllerInputPort = config.registerRestaurantControllerInputPort(
                findById,
                findAllByUserId,
                registerUseCase,
                updateUseCase,
                deleteUseCase,
                findByIdUserUseCase,
                getUserIdByLoginUseCase,
                mapper
        );

        assertNotNull(controllerInputPort);
    }

    @Test
    void testRegisterDeleteRestaurantUseCase() {
        RestaurantGateway gateway = mock(RestaurantGateway.class);

        DeleteRestaurantUseCase useCase = config.registerDeleteRestaurantUseCase(gateway);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterFindAllByUserIdRestaurantUseCase() {
        RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);
        AddressGateway addressGateway = mock(AddressGateway.class);
        OpeningHourGateway openingHourGateway = mock(OpeningHourGateway.class);
        RestaurantMapper mapper = new RestaurantMapper(new AddressMapper(), new OpeningHourMapper());

        FindAllByUserIdRestaurantUseCase useCase = config.registerFindAllByUserIdRestaurantUseCase(
                restaurantGateway,
                addressGateway,
                openingHourGateway,
                mapper
        );

        assertNotNull(useCase);
    }

    @Test
    void testRegisterFindByIdRestaurantUseCase() {
        RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);
        AddressGateway addressGateway = mock(AddressGateway.class);
        OpeningHourGateway openingHourGateway = mock(OpeningHourGateway.class);
        RestaurantMapper mapper = new RestaurantMapper(new AddressMapper(), new OpeningHourMapper());

        FindByIdRestaurantUseCase useCase = config.registerFindByIdRestaurantUseCase(
                restaurantGateway,
                addressGateway,
                openingHourGateway,
                mapper
        );

        assertNotNull(useCase);
    }

    @Test
    void testRegisterRegisterRestaurantUseCase() {
        RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);
        AddressGateway addressGateway = mock(AddressGateway.class);
        OpeningHourGateway openingHourGateway = mock(OpeningHourGateway.class);
        RestaurantMapper mapper = new RestaurantMapper(new AddressMapper(), new OpeningHourMapper());
        List<OpeningHourValidator> validators = List.of();

        RegisterRestaurantUseCase useCase = config.registerRegisterRestaurantUseCase(
                restaurantGateway,
                addressGateway,
                openingHourGateway,
                mapper,
                validators
        );

        assertNotNull(useCase);
    }

    @Test
    void testRegisterUpdateRestaurantUseCase() {
        RestaurantGateway restaurantGateway = mock(RestaurantGateway.class);
        AddressGateway addressGateway = mock(AddressGateway.class);
        OpeningHourGateway openingHourGateway = mock(OpeningHourGateway.class);
        RestaurantMapper mapper = new RestaurantMapper(new AddressMapper(), new OpeningHourMapper());
        List<OpeningHourValidator> validators = List.of();

        UpdateRestaurantUseCase useCase = config.registerUpdateRestaurantUseCase(
                restaurantGateway,
                addressGateway,
                openingHourGateway,
                mapper,
                validators
        );

        assertNotNull(useCase);
    }
}
