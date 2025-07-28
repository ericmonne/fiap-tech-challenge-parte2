package com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantEntity;

import java.util.List;

public interface IRestaurantMapper {

    RestaurantResponseDTO toResponseDTO(Restaurant restaurant);

    List<RestaurantResponseDTO> toResponseDTO(List<Restaurant> restaurant);

    Restaurant toEntity(RestaurantRequestDTO dto, User user);

    JdbcRestaurantEntity toJdbcRestaurantEntity(Restaurant restaurant);
}
