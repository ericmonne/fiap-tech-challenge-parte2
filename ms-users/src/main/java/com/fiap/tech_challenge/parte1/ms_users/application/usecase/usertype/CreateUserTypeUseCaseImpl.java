package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.CreateUserTypeUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserTypeNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

public class CreateUserTypeUseCaseImpl implements CreateUserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    private final com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper iUserTypeMapper;

    public CreateUserTypeUseCaseImpl(
            final UserTypeGateway userTypeGateway, com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper iUserTypeMapper
    ) {
        this.userTypeGateway = userTypeGateway;
        this.iUserTypeMapper = iUserTypeMapper;
    }

    @Override
    public UserTypeResponseDTO execute(final UserTypeRequestDTO userTypeRequestDTO) {
        var convertType = this.iUserTypeMapper.toUserType(userTypeRequestDTO);
        Long generatedId = this.userTypeGateway.createUserType(convertType);
        UserType userType = userTypeGateway.findById(generatedId).orElseThrow(() -> new UserTypeNotFoundException("User type not created"));
        return iUserTypeMapper.toUserTypeResponseDto(userType);
    }
}
