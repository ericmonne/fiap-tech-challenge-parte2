package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.address.IAddressMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.openinghour.IOpeningHourMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.restaurant.IRestaurantMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant.JdbcRestaurantEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper implements IRestaurantMapper {

    private final IAddressMapper iAddressMapper;
    private final IOpeningHourMapper iOpeningHourMapper;

    public RestaurantMapper(IAddressMapper iAddressMapper, IOpeningHourMapper iOpeningHourMapper) {
        this.iAddressMapper = iAddressMapper;
        this.iOpeningHourMapper = iOpeningHourMapper;
    }

    @Override
    public RestaurantResponseDTO toResponseDTO(Restaurant restaurant) {
        return new RestaurantResponseDTO(
                restaurant.getId(),
                restaurant.getName(),
                iAddressMapper.toAddressResponseDTO(restaurant.getAddress()),
                restaurant.getCuisineType(),
                iOpeningHourMapper.toOpeningHourResponseDTO(restaurant.getOpeningHours())
        );
    }

    @Override
    public List<RestaurantResponseDTO> toResponseDTO(List<Restaurant> restaurant) {
        return restaurant.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public Restaurant toEntity(RestaurantRequestDTO dto, User user) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.name());
        restaurant.setAddress(iAddressMapper.toEntity(dto.address()));
        restaurant.setCuisineType(dto.cuisineType());
        restaurant.setOpeningHours(iOpeningHourMapper.toEntity(dto.openingHours()));
        restaurant.setUser(user);
        return restaurant;
    }

    @Override
    public JdbcRestaurantEntity toJdbcRestaurantEntity(Restaurant restaurant) {
        JdbcRestaurantEntity restaurantEntity = new JdbcRestaurantEntity();
        restaurantEntity.setId(restaurant.getId());
        restaurantEntity.setName(restaurant.getName());
        restaurantEntity.setCuisineType(restaurant.getCuisineType());
        restaurantEntity.setUserId(restaurant.getUser().getId());
        return restaurantEntity;
    }

}
