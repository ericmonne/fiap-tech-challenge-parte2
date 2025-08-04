package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.FindByIdUserTypeUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserTypeNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

public class FindByIdUserTypeUseCaseImpl implements FindByIdUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public FindByIdUserTypeUseCaseImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public UserType execute(Long id) {
        return this.userTypeGateway.findById(id).orElseThrow(()
                -> new UserTypeNotFoundException("ID not found for update"));
    }
}
