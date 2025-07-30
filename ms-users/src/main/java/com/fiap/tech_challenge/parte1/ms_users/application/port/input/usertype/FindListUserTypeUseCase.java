package com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

import java.util.List;

public interface FindListUserTypeUseCase {

    List<UserType> execute(int size, int page);
}
