package com.fiap.tech_challenge.parte1.ms_users.application.controller.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.controller.MenuItemControllerInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class MenuItemControllerInputPortImpl implements MenuItemControllerInputPort {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemControllerInputPortImpl.class.getName());
    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final FindMenuItemByIdUseCase findMenuItemByIdUseCase;
    private final ReadPaginatedMenuItemsUseCase readPaginatedMenuItemsUseCase;
    private final ReadAllMenuItemsUseCase readAllMenuItemsUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final ChangeMenuItemAvailabilityUseCase changeMenuItemAvailabilityUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;


    public MenuItemControllerInputPortImpl(CreateMenuItemUseCase createMenuItemUseCase, FindMenuItemByIdUseCase findMenuItemByIdUseCase, ReadPaginatedMenuItemsUseCase readPaginatedMenuItemsUseCase, ReadAllMenuItemsUseCase readAllMenuItemsUseCase, UpdateMenuItemUseCase updateMenuItemUseCase, ChangeMenuItemAvailabilityUseCase changeMenuItemAvailabilityUseCase, DeleteMenuItemUseCase deleteMenuItemUseCase) {
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.findMenuItemByIdUseCase = findMenuItemByIdUseCase;
        this.readPaginatedMenuItemsUseCase = readPaginatedMenuItemsUseCase;
        this.readAllMenuItemsUseCase = readAllMenuItemsUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.changeMenuItemAvailabilityUseCase = changeMenuItemAvailabilityUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
    }

    @Override
    public MenuItemResponseDTO create(CreateMenuItemDTO createMenuItemDTO) {
        logger.info("Creating menu item -> {}", createMenuItemDTO);
        return createMenuItemUseCase.execute(createMenuItemDTO);
    }

    @Override
    public MenuItemResponseDTO update(UUID id, UpdateMenuItemDTO updateMenuItemDTO) {
        logger.info("Updating menu item -> {}", updateMenuItemDTO);
        return updateMenuItemUseCase.execute(id, updateMenuItemDTO);
    }

    @Override
    public MenuItemResponseDTO getById(UUID id) {
        logger.info("Getting menu item by id -> {}", id);
        return findMenuItemByIdUseCase.execute(id);
    }

    @Override
    public PaginatedResponseDTO<MenuItemResponseDTO> findAllPaginated(int size, int offset) {
        logger.info("Getting paginated menu items -> size: {} ,  offset: {}", size, offset);
        return readPaginatedMenuItemsUseCase.execute(size, offset);
    }

    @Override
    public List<MenuItemResponseDTO> findAll() {
        logger.info("Getting all menu items");
        return readAllMenuItemsUseCase.execute();
    }

    @Override
    public void delete(UUID id) {
        logger.info("Deleting menu item -> {}", id);
        deleteMenuItemUseCase.execute(id);
    }

    @Override
    public MenuItemResponseDTO changeAvailability(UUID id, Boolean availableOnlyOnSite) {
        logger.info("Changing menu item availability -> id: {}, availableOnlyOnSite: {}", id, availableOnlyOnSite);
        return changeMenuItemAvailabilityUseCase.execute(id, availableOnlyOnSite);
    }
}
