package com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.cuisinetype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.cuisinetype.CuisineTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.cuisinetype.CuisineTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;

import java.util.List;

public interface ICuisineTypeMapper {
    CuisineTypeResponseDTO toCuisineTypeResponseDTO(CuisineType cuisineType);

    List<CuisineTypeResponseDTO> toCuisineTypeResponseDTO(List<CuisineType> cuisineTypes);

    List<CuisineType> toEntity(List<CuisineTypeRequestDTO> cuisineTypeRequestDTOS);

//    List<JdbcAddressEntity> toJdbcAddressEntity(List<Address> addresses);
}
