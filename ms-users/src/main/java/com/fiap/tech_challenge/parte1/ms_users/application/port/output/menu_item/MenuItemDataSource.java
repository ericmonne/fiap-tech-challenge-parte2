package com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemDataSource {

    PaginatedResult<MenuItem> findAllPaginated(int size, int offset);

    List<MenuItem> findAll();

    /**
     * Saves a new menu item to the database.
     *
     * @param menuItem the menu item to save
     */
    MenuItem save(MenuItem menuItem);

    /**
     * Finds a menu item by its unique identifier.
     *
     * @param id the UUID of the menu item to find
     * @return the menu item if found, or null if no menu item with the given ID exists
     */
    Optional<MenuItem> findById(UUID id);

    /**
     * Deletes a menu item by its unique identifier.
     *
     * @param id the UUID of the menu item to delete
     */
    void deleteById(UUID id);

    /**
     * Updates an existing menu item in the database.
     *
     * @param menuItem the menu item with updated values
     */
    void update(MenuItem menuItem);

    boolean existsById(UUID id);

    Optional<MenuItem> findByName(String name);

    PaginatedResult<MenuItem> findByRestaurantId(MenuItemsByRestaurantRequestDTO restaurantRequestDTO);
}
