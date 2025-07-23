package com.fiap.tech_challenge.parte1.ms_users.application.usecase;

import com.fiap.tech_challenge.parte1.ms_users.dtos.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.dtos.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.entities.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.exceptions.MenuItemAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.mappers.MenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case for creating a new menu item.
 * Handles the business logic for menu item creation including validation.
 */
@Service
public class CreateMenuItemUseCase {
    
    private final MenuItemRepository repository;
    private final MenuItemMapper menuItemMapper;

    public CreateMenuItemUseCase(MenuItemRepository repository) {
        this.repository = repository;
        this.menuItemMapper = new MenuItemMapper();
    }

    /**
     * Executes the menu item creation process.
     *
     * @param requestDTO the DTO containing the menu item data
     * @return the created menu item as a response DTO
     * @throws MenuItemAlreadyExistsException if a menu item with the same name already exists
     */
    public MenuItemResponseDTO execute(CreateMenuItemDTO requestDTO) {
        validateMenuItemDoesNotExist(requestDTO.name());
        
        MenuItem newMenuItem = createMenuItemFromDTO(requestDTO);
        repository.save(newMenuItem);
        
        return menuItemMapper.toResponseDTO(newMenuItem);
    }
    
    /**
     * Validates that a menu item with the given name doesn't already exist.
     * 
     * @param name the name to check
     * @throws MenuItemAlreadyExistsException if a menu item with the name already exists
     */
    private void validateMenuItemDoesNotExist(String name) {
        boolean exists = repository.findAll().stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(name));
                
        if (exists) {
            throw new MenuItemAlreadyExistsException("A menu item with name '" + name + "' already exists");
        }
    }
    
    /**
     * Creates a new MenuItem entity from the request DTO.
     * 
     * @param dto the request DTO
     * @return a new MenuItem instance
     */
    private MenuItem createMenuItemFromDTO(CreateMenuItemDTO dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(UUID.randomUUID());
        menuItem.setName(dto.name());
        menuItem.setDescription(dto.description());
        menuItem.setPrice(dto.price());
        menuItem.setAvailableOnlyOnSite(dto.availableOnlyOnSite());
        menuItem.setImagePath(dto.imagePath());
        
        return menuItem;
    }
}
