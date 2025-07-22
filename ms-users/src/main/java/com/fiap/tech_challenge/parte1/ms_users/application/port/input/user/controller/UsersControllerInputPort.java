package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.*;

import java.util.List;
import java.util.UUID;

public interface UsersControllerInputPort {
    UsersResponseDTO getById(UUID id);

    List<UsersResponseDTO> findAllUsers(int size, int page);

    CreateUserDTO create(UsersRequestDTO dto);

    TokenJWTInfoDTO executeLogin(AuthenticationDataDTO data);

    String toggleActivation(UUID id, boolean activate);

    String changePassword(UUID id, ChangePasswordRequestDTO dto);

    UsersResponseDTO updateUser(UUID id, UpdateUserDTO dto);
}
