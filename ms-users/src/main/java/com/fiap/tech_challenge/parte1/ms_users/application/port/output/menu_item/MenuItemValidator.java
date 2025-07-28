package com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;

public interface MenuItemValidator {
    void validate(MenuItem menuItem);
}
