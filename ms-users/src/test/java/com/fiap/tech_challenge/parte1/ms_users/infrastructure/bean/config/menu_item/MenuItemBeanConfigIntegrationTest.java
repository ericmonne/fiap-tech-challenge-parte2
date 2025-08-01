package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.controller.MenuItemControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
//TODO - is failing, should configure migrations
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MenuItemBeanConfigIntegrationTest {

    @Autowired
    private MenuItemDataSource menuItemDataSource;

    @Autowired
    private MenuItemGateway menuItemGateway;

    @Autowired
    private CreateMenuItemUseCase createMenuItemUseCase;

    @Autowired
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @Autowired
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @Autowired
    private ReadAllMenuItemsUseCase readAllMenuItemsUseCase;

    @Autowired
    private ReadPaginatedMenuItemsUseCase readPaginatedMenuItemsUseCase;

    @Autowired
    private FindMenuItemByIdUseCase findMenuItemByIdUseCase;

    @Autowired
    private ChangeMenuItemAvailabilityUseCase changeMenuItemAvailabilityUseCase;

    @Autowired
    private FindMenuItemsByRestaurantIdUseCase findMenuItemsByRestaurantIdUseCase;

    @Autowired
    private MenuItemControllerInputPort controllerInputPort;

    @Test
    void allBeansShouldBeLoadedSuccessfully() {
        assertThat(menuItemDataSource).isNotNull();
        assertThat(menuItemGateway).isNotNull();
        assertThat(createMenuItemUseCase).isNotNull();
        assertThat(updateMenuItemUseCase).isNotNull();
        assertThat(deleteMenuItemUseCase).isNotNull();
        assertThat(readAllMenuItemsUseCase).isNotNull();
        assertThat(readPaginatedMenuItemsUseCase).isNotNull();
        assertThat(findMenuItemByIdUseCase).isNotNull();
        assertThat(changeMenuItemAvailabilityUseCase).isNotNull();
        assertThat(findMenuItemsByRestaurantIdUseCase).isNotNull();
        assertThat(controllerInputPort).isNotNull();
    }
}
