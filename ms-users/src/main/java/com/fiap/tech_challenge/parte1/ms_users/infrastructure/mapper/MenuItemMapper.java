package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item.JdbcMenuItemEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between MenuItem entities and DTOs.
 */
@Component
public class MenuItemMapper implements IMenuItemMapper {

    /**
     * Converts a list of MenuItem entities to a list of MenuItemsResponseDTOs.
     *
     * @param items the list of MenuItem entities to convert
     * @return a list of converted MenuItemsResponseDTOs, or an empty list if the input is null or empty
     */
    public List<MenuItemResponseDTO> mapList(List<MenuItem> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }

        return items.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a MenuItem entity to a MenuItemsResponseDTO.
     *
     * @param menuItem the MenuItem entity to convert
     * @return the converted MenuItemsResponseDTO, or null if the input is null
     */
    public MenuItemResponseDTO toResponseDTO(MenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }

        return new MenuItemResponseDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableOnlyOnSite(),
                menuItem.getImagePath(),
                menuItem.getRestaurantId()
        );
    }

    @Override
    public MenuItem toEntity(CreateMenuItemDTO createMenuItemDTO) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(createMenuItemDTO.name());
        menuItem.setDescription(createMenuItemDTO.description());
        menuItem.setPrice(createMenuItemDTO.price());
        menuItem.setAvailableOnlyOnSite(createMenuItemDTO.availableOnlyOnSite());
        menuItem.setImagePath(createMenuItemDTO.imagePath());
        return menuItem;
    }

    @Override
    public JdbcMenuItemEntity toJdbcMenuItemEntity(MenuItem menuItem) {
        JdbcMenuItemEntity jdbcMenuItemEntity = new JdbcMenuItemEntity();
        jdbcMenuItemEntity.setId(menuItem.getId());
        jdbcMenuItemEntity.setName(menuItem.getName());
        jdbcMenuItemEntity.setDescription(menuItem.getDescription());
        jdbcMenuItemEntity.setPrice(menuItem.getPrice());
        jdbcMenuItemEntity.setAvailableOnlyOnSite(menuItem.getAvailableOnlyOnSite());
        jdbcMenuItemEntity.setImagePath(menuItem.getImagePath());
        return jdbcMenuItemEntity;
    }

    @Override
    public MenuItem toEntity(UpdateMenuItemDTO updateMenuItemDTO) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(updateMenuItemDTO.name());
        menuItem.setDescription(updateMenuItemDTO.description());
        menuItem.setPrice(updateMenuItemDTO.price());
        menuItem.setAvailableOnlyOnSite(updateMenuItemDTO.availableOnlyOnSite());
        menuItem.setImagePath(updateMenuItemDTO.imagePath());
        return menuItem;
    }


}
