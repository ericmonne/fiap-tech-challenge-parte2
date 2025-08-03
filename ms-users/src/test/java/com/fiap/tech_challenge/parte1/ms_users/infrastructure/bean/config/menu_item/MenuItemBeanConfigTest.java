package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.menu_item.MenuItemGatewayImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemBeanConfigTest {

    @Mock
    private MenuItemDataSource menuItemDataSource;
    @Mock
    private MenuItemGatewayImpl menuItemGateway;

    @BeforeEach
    void setUp() {
        menuItemGateway = new MenuItemGatewayImpl(menuItemDataSource);
    }

    @Test
    void testSaveShouldCallDataSourceAndReturnMenuItem() {
        MenuItem menuItem = mock(MenuItem.class);
        when(menuItemDataSource.save(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemGateway.save(menuItem);

        assertThat(result).isEqualTo(menuItem);
        verify(menuItemDataSource).save(menuItem);
    }
}

