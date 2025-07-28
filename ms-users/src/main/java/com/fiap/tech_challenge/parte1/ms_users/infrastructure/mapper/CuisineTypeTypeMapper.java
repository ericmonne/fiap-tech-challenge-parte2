package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.cuisinetype.CuisineTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.cuisinetype.CuisineTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.cuisinetype.ICuisineTypeMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CuisineTypeTypeMapper implements ICuisineTypeMapper {

    @Override
    public CuisineTypeResponseDTO toCuisineTypeResponseDTO(CuisineType cuisineType) {
        return null;
    }

    @Override
    public List<CuisineTypeResponseDTO> toCuisineTypeResponseDTO(List<CuisineType> cuisineTypes) {
        return List.of();
    }

    @Override
    public List<CuisineType> toEntity(List<CuisineTypeRequestDTO> cuisineTypeRequestDTOS) {
        return List.of();
    }
}
