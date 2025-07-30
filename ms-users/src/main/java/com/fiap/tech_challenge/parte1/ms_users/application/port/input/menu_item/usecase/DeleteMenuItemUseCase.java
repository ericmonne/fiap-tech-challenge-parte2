package com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase;

import java.util.UUID;

public interface DeleteMenuItemUseCase {
    void execute(UUID id);
}
