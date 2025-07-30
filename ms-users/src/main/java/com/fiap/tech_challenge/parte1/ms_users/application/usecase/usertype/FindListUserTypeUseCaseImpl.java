package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.FindListUserTypeUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

import java.util.List;

public class FindListUserTypeUseCaseImpl implements FindListUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public FindListUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public List<UserType> execute(int size, int page) {
        var offset = (page - 1) * size;
        return this.userTypeGateway.findAll(size,offset);
    }
}
