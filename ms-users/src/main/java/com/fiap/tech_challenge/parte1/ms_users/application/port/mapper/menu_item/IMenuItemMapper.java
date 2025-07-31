package com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item.JdbcMenuItemEntity;

import java.util.List;

public interface IMenuItemMapper {
    List<MenuItemResponseDTO> mapList(List<MenuItem> items);

    MenuItemResponseDTO toResponseDTO(MenuItem menuItem);

    MenuItem toEntity(CreateMenuItemDTO createMenuItemDTO);

    JdbcMenuItemEntity toJdbcMenuItemEntity(MenuItem menuItem);

    MenuItem toEntity(UpdateMenuItemDTO updateMenuItemDTO, MenuItem existingMenuItem);

}
