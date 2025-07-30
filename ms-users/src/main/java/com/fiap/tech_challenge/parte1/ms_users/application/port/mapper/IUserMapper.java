package com.fiap.tech_challenge.parte1.ms_users.application.port.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UpdateUserDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UsersRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UsersResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserEntity;

import java.util.List;
import java.util.UUID;

public interface IUserMapper {
    UsersResponseDTO toResponseDTO(User user);

    List<UsersResponseDTO> toResponseDTO(List<User> users);

    User toEntity(UsersRequestDTO dto);

    User toEntity(UpdateUserDTO dto, UUID userId);

    JdbcUserEntity toJdbcUserEntity(User user);
}

