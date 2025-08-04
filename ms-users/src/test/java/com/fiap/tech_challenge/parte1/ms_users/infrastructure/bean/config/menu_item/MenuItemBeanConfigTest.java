package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.controller.MenuItemControllerInputPortImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.controller.MenuItemControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.validator.UniqueMenuItemNameValidator;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.menu_item.MenuItemGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item.JdbcMenuItemDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item.JdbcMenuItemRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MenuItemBeanConfigTest {

    private final MenuItemBeanConfig config = new MenuItemBeanConfig();

    @Test
    void testRegisterMenuItemDataSource() {
        JdbcMenuItemRepository repository = mock(JdbcMenuItemRepository.class);
        IMenuItemMapper mapper = mock(IMenuItemMapper.class);

        MenuItemDataSource dataSource = config.registerMenuItemDataSource(repository, mapper);

        assertNotNull(dataSource);
        assertInstanceOf(JdbcMenuItemDataSource.class, dataSource);
    }

    @Test
    void testRegisterMenuItemGateway() {
        MenuItemDataSource dataSource = mock(MenuItemDataSource.class);

        MenuItemGateway gateway = config.registerMenuItemGateway(dataSource);

        assertNotNull(gateway);
        assertInstanceOf(MenuItemGatewayImpl.class, gateway);
    }

    @Test
    void testRegisterCreateMenuItemUseCase() {
        MenuItemGateway gateway = mock(MenuItemGateway.class);
        IMenuItemMapper mapper = mock(IMenuItemMapper.class);
        List<MenuItemValidator> validators = List.of();

        CreateMenuItemUseCase useCase = config.registerCreateMenuItemUseCase(gateway, mapper, validators);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterUpdateMenuItemUseCase() {
        MenuItemGateway gateway = mock(MenuItemGateway.class);
        IMenuItemMapper mapper = mock(IMenuItemMapper.class);
        List<MenuItemValidator> validators = List.of();

        UpdateMenuItemUseCase useCase = config.registerUpdateMenuItemUseCase(gateway, mapper, validators);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterMenuItemValidators() {
        MenuItemGateway gateway = mock(MenuItemGateway.class);

        List<MenuItemValidator> validators = config.registerMenuItemValidators(gateway);

        assertNotNull(validators);
        assertFalse(validators.isEmpty());
        assertInstanceOf(UniqueMenuItemNameValidator.class, validators.get(0));
    }

    @Test
    void testRegisterDeleteMenuItemUseCase() {
        MenuItemGateway gateway = mock(MenuItemGateway.class);

        DeleteMenuItemUseCase useCase = config.registerDeleteMenuItemUseCase(gateway);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterReadAllMenuItemsUseCase() {
        MenuItemGateway gateway = mock(MenuItemGateway.class);
        IMenuItemMapper mapper = mock(IMenuItemMapper.class);

        ReadAllMenuItemsUseCase useCase = config.registerReadAllMenuItemsUseCase(gateway, mapper);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterReadPaginatedMenuItemsUseCase() {
        MenuItemGateway gateway = mock(MenuItemGateway.class);
        IMenuItemMapper mapper = mock(IMenuItemMapper.class);

        ReadPaginatedMenuItemsUseCase useCase = config.registerReadPaginatedMenuItemsUseCase(gateway, mapper);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterFindMenuItemByIdUseCase() {
        MenuItemGateway gateway = mock(MenuItemGateway.class);
        IMenuItemMapper mapper = mock(IMenuItemMapper.class);

        FindMenuItemByIdUseCase useCase = config.registerFindMenuItemByIdUseCase(gateway, mapper);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterChangeMenuItemAvailabilityUseCase() {
        MenuItemGateway gateway = mock(MenuItemGateway.class);
        IMenuItemMapper mapper = mock(IMenuItemMapper.class);

        ChangeMenuItemAvailabilityUseCase useCase = config.registerChangeMenuItemAvailabilityUseCase(gateway, mapper);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterFindMenuItemsByRestaurantIdUseCase() {
        MenuItemGateway gateway = mock(MenuItemGateway.class);
        IMenuItemMapper mapper = mock(IMenuItemMapper.class);

        FindMenuItemsByRestaurantIdUseCase useCase = config.registerFindMenuItemsByRestaurantIdUseCase(gateway, mapper);

        assertNotNull(useCase);
    }

    @Test
    void testRegisterMenuItemControllerInputPort() {
        CreateMenuItemUseCase createUseCase = mock(CreateMenuItemUseCase.class);
        FindMenuItemByIdUseCase findByIdUseCase = mock(FindMenuItemByIdUseCase.class);
        ReadPaginatedMenuItemsUseCase readPaginatedUseCase = mock(ReadPaginatedMenuItemsUseCase.class);
        ReadAllMenuItemsUseCase readAllUseCase = mock(ReadAllMenuItemsUseCase.class);
        UpdateMenuItemUseCase updateUseCase = mock(UpdateMenuItemUseCase.class);
        ChangeMenuItemAvailabilityUseCase changeAvailabilityUseCase = mock(ChangeMenuItemAvailabilityUseCase.class);
        DeleteMenuItemUseCase deleteUseCase = mock(DeleteMenuItemUseCase.class);
        FindMenuItemsByRestaurantIdUseCase findByRestaurantIdUseCase = mock(FindMenuItemsByRestaurantIdUseCase.class);

        MenuItemControllerInputPort controllerInputPort = config.registerMenuItemControllerInputPort(
                createUseCase,
                findByIdUseCase,
                readPaginatedUseCase,
                readAllUseCase,
                updateUseCase,
                changeAvailabilityUseCase,
                deleteUseCase,
                findByRestaurantIdUseCase
        );

        assertNotNull(controllerInputPort);
        assertInstanceOf(MenuItemControllerInputPortImpl.class, controllerInputPort);
    }
}
