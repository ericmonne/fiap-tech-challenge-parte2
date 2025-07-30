package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.FindByIdUserTypeUseCase;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.usertype.UserTypeGatewayImpl;

public class FindByIdUserTypeUseCaseImpl implements FindByIdUserTypeUseCase {

    private final UserTypeGatewayImpl userTypeGateway;

    public FindByIdUserTypeUseCaseImpl(UserTypeGatewayImpl userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public UserType execute(Long id) {
        return this.userTypeGateway.findById(id).orElseThrow(()
                -> new RuntimeException("ID not found for update"));
    }
}
