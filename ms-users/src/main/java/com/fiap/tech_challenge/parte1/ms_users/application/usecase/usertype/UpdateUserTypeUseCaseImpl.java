package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.UpdateUserTypeUseCase;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.usertype.UserTypeGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.UserTypeMapper;

public class UpdateUserTypeUseCaseImpl implements UpdateUserTypeUseCase {

    private final UserTypeGatewayImpl userTypeGateway;

    public UpdateUserTypeUseCaseImpl(UserTypeGatewayImpl userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public void execute(UserType userType){
        this.userTypeGateway.update(userType);
    }
}
