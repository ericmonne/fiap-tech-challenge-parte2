package com.fiap.tech_challenge.parte1.ms_users.mappers;

import com.fiap.tech_challenge.parte1.ms_users.dtos.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.entities.MenuItem;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between MenuItem entities and DTOs.
 */
@Component
public class MenuItemMapper {

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
            menuItem.getImagePath()
        );
    }
}
