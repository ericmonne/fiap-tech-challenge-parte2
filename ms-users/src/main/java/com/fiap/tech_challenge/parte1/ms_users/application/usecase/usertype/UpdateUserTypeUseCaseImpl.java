package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.UpdateUserTypeUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.usertype.UserTypeGatewayImpl;

public class UpdateUserTypeUseCaseImpl implements UpdateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public UpdateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public void execute(UserType userType){
        this.userTypeGateway.update(userType);
    }
}
