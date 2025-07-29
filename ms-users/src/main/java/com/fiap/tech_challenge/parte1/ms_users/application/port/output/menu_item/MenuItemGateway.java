package com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemGateway {
    MenuItem save(MenuItem menuItem);

    void update(MenuItem menuItem);

    void deleteById(UUID id);

    Optional<MenuItem> findById(UUID id);

    /**
     * Retrieves a paginated list of menu items.
     *
     * @param size   the maximum number of items to retrieve per page
     * @param offset the starting point for the items to retrieve
     * @return a PaginatedResult containing the list of menu items within the given pagination window
     */
    PaginatedResult<MenuItem> findPaginated(int size, int offset);

    List<MenuItem> findAll();

    boolean existsById(UUID id);

    Optional<MenuItem> findByName(String name);

}
