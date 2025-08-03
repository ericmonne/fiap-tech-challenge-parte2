package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.UpdateUserTypeUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

public class UpdateUserTypeUseCaseImpl implements UpdateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;
    private final IUserTypeMapper iUserTypeMapper;

    public UpdateUserTypeUseCaseImpl(UserTypeGateway userTypeGateway, IUserTypeMapper iUserTypeMapper) {
        this.userTypeGateway = userTypeGateway;
        this.iUserTypeMapper = iUserTypeMapper;
    }

    @Override
    public UserTypeResponseDTO execute(UserType userType) {
        userTypeGateway.findById(userType.getId()).orElseThrow(() -> new RuntimeException("User type not found"));
        this.userTypeGateway.update(userType);
        return iUserTypeMapper.toUserTypeResponseDto(userTypeGateway.findById(userType.getId()).orElseThrow(() -> new RuntimeException("User type not found")));
    }
}
