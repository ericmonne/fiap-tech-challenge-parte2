package com.fiap.tech_challenge.parte1.ms_users.repositories;

import com.fiap.tech_challenge.parte1.ms_users.entities.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.exceptions.ResourceNotFoundException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class MenuItemRepositoryImpl implements MenuItemRepository {

    private final JdbcClient jdbcClient;

    public MenuItemRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }




    @Override
    public List<MenuItem> findAll() {
        final String sql = """
                SELECT
                    name,
                    description,
                    price,
                    available_only_on_site,
                    image_path
                FROM
                    menu_item
                """;

        return jdbcClient
                .sql(sql)
                .query(MenuItem.class)
                .list();
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(MenuItem menuItem) {
        final String sql = """
                    INSERT INTO menu_item (
                        id, name, description, price, available_only_on_site, image_path
                    ) VALUES (
                        :id, :name, :description, :price, :availableOnlyOnSite, :imagePath
                    );
                """;
        jdbcClient.sql(sql)
                .param("id", menuItem.getId())
                .param("name", menuItem.getName())
                .param("description", menuItem.getDescription())
                .param("price", menuItem.getPrice())
                .param("availableOnlyOnSite", menuItem.getAvailableOnlyOnSite())
                .param("imagePath", menuItem.getImagePath())
                .update();

    }

    @Override
    public MenuItem findById(UUID id) {
        final String sql = "SELECT * FROM menu_item WHERE id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(MenuItem.class)
                .optional()
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(UUID id) {
        final String sql = "DELETE * FROM menu_item WHERE id = :id";
        jdbcClient.sql(sql)
                .param("id", id)
                .query(MenuItem.class)
                .optional()
                .orElseThrow(ResourceNotFoundException::new);

    }
}
