package com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserTypeControllerInputPort {

    void create(final UserTypeRequestDTO userTypeRequestDTO);

    void update(final UserTypeRequestDTO userTypeRequestDTO);

    String toggleActivationUserType(Long id, boolean activate);

    UserTypeResponseDTO getById(final Long id);

    List<UserTypeResponseDTO> findAllUserType(final int size, final int page);
}
