package com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

public interface FindByIdUserTypeUseCase {

    UserType execute(Long id);
}
