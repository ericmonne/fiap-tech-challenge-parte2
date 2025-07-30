package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.DeactivateUserTypeUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;

public class DeactivateUserTypeUseCaseImpl implements DeactivateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public DeactivateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public void execute(Long id) {
        this.userTypeGateway.deactivate(id);
    }
}