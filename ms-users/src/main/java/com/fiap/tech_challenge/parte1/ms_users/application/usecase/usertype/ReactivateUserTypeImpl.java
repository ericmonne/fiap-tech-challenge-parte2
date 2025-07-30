package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.ReactivateUserTypeUserCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;

public class ReactivateUserTypeImpl implements ReactivateUserTypeUserCase {

    private final UserTypeGateway userTypeGateway;

    public ReactivateUserTypeImpl(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }

    @Override
    public void execute(final Long id) {
        this.userTypeGateway.reactivate(id);
    }
}
