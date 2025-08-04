package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.controller.MenuItemControllerInputPortImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.controller.MenuItemControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item.*;
import com.fiap.tech_challenge.parte1.ms_users.domain.validator.MenuItemPriceValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.validator.UniqueMenuItemNameValidator;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.menu_item.MenuItemGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item.JdbcMenuItemDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item.JdbcMenuItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MenuItemBeanConfig {

    @Bean
    MenuItemDataSource registerMenuItemDataSource(JdbcMenuItemRepository jdbcMenuItemRepository, IMenuItemMapper menuItemMapper) {
        return new JdbcMenuItemDataSource(jdbcMenuItemRepository, menuItemMapper);
    }

    @Bean
    public MenuItemGateway registerMenuItemGateway(MenuItemDataSource menuItemDataSource) {
        return new MenuItemGatewayImpl(menuItemDataSource);
    }

    @Bean
    public CreateMenuItemUseCase registerCreateMenuItemUseCase(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper, List<MenuItemValidator> menuItemValidators) {
        return new CreateMenuItemUseCaseImpl(menuItemGateway, menuItemMapper, menuItemValidators);
    }

    @Bean
    public UpdateMenuItemUseCase registerUpdateMenuItemUseCase(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper, List<MenuItemValidator> menuItemValidators) {
        return new UpdateMenuItemUseCaseImpl(menuItemGateway, menuItemMapper, menuItemValidators);
    }

    @Bean
    public List<MenuItemValidator> registerMenuItemValidators(MenuItemGateway menuItemGateway) {
        return List.of(new UniqueMenuItemNameValidator(menuItemGateway), new MenuItemPriceValidator());
    }

    @Bean
    public DeleteMenuItemUseCase registerDeleteMenuItemUseCase(MenuItemGateway menuItemGateway) {
        return new DeleteMenuItemUseCaseImpl(menuItemGateway);
    }

    @Bean
    public ReadAllMenuItemsUseCase registerReadAllMenuItemsUseCase(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper) {
        return new ReadAllMenuItemsUseCaseImpl(menuItemGateway, menuItemMapper);
    }

    @Bean
    public ReadPaginatedMenuItemsUseCase registerReadPaginatedMenuItemsUseCase(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper) {
        return new ReadPaginatedMenuItemsUseCaseImpl(menuItemGateway, menuItemMapper);
    }

    @Bean
    public FindMenuItemByIdUseCase registerFindMenuItemByIdUseCase(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper) {
        return new FindMenuItemByIdUseCaseImpl(menuItemGateway, menuItemMapper);
    }

    @Bean
    public ChangeMenuItemAvailabilityUseCase registerChangeMenuItemAvailabilityUseCase(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper) {
        return new ChangeMenuItemAvailabilityUseCaseImpl(menuItemGateway, menuItemMapper);
    }

    @Bean
    public FindMenuItemsByRestaurantIdUseCase registerFindMenuItemsByRestaurantIdUseCase(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper) {
        return new FindMenuItemsByRestaurantIdUseCaseImpl(menuItemGateway, menuItemMapper);
    }

    @Bean
    public MenuItemControllerInputPort registerMenuItemControllerInputPort(CreateMenuItemUseCase createMenuItemUseCase,
                                                                           FindMenuItemByIdUseCase findMenuItemByIdUseCase,
                                                                           ReadPaginatedMenuItemsUseCase readPaginatedMenuItemsUseCase,
                                                                           ReadAllMenuItemsUseCase readAllMenuItemsUseCase,
                                                                           UpdateMenuItemUseCase updateMenuItemUseCase,
                                                                           ChangeMenuItemAvailabilityUseCase changeMenuItemAvailabilityUseCase,
                                                                           DeleteMenuItemUseCase deleteMenuItemUseCase,
                                                                           FindMenuItemsByRestaurantIdUseCase findMenuItemsByRestaurantIdUseCase) {
        return new MenuItemControllerInputPortImpl(createMenuItemUseCase,
                findMenuItemByIdUseCase,
                readPaginatedMenuItemsUseCase,
                readAllMenuItemsUseCase,
                updateMenuItemUseCase,
                changeMenuItemAvailabilityUseCase,
                deleteMenuItemUseCase,
                findMenuItemsByRestaurantIdUseCase);
    }
}
