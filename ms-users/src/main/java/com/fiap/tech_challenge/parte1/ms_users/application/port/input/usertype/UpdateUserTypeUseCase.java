package com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

public interface UpdateUserTypeUseCase {

    UserTypeResponseDTO execute(final UserType userType);
}
