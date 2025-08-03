package com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;

import java.util.List;

public interface UserTypeControllerInputPort {

    UserTypeResponseDTO create(final UserTypeRequestDTO userTypeRequestDTO);

    UserTypeResponseDTO update(final UserTypeRequestDTO userTypeRequestDTO, Long id);

    String toggleActivationUserType(Long id, boolean activate);

    UserTypeResponseDTO findById(final Long id);

    List<UserTypeResponseDTO> findAllUserType(final int size, final int page);
}
