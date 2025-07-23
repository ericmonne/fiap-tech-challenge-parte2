package com.fiap.tech_challenge.parte1.ms_users.repositories;

import com.fiap.tech_challenge.parte1.ms_users.entities.MenuItem;

import java.util.List;
import java.util.UUID;

public interface MenuItemRepository {

    /**
     * Retrieves a list of all menu items in the database.
     *
     * @return a list of all menu items
     */
    List<MenuItem> findAll();

    /**
     * Saves a new menu item to the database.
     *
     * @param menuItem the menu item to save
     */
    void save(MenuItem menuItem);

    /**
     * Finds a menu item by its unique identifier.
     *
     * @param id the UUID of the menu item to find
     * @return the menu item if found, or null if no menu item with the given ID exists
     */
    MenuItem findById(UUID id);

    /**
     * Deletes a menu item by its unique identifier.
     *
     * @param id the UUID of the menu item to delete
     */
    void deleteById(UUID id);
}
