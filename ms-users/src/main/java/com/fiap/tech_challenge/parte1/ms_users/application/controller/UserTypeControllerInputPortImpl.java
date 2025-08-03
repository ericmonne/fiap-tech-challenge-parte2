package com.fiap.tech_challenge.parte1.ms_users.application.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserTypeControllerInputPortImpl implements UserTypeControllerInputPort {

    private final CreateUserTypeUseCase createUserTypeUseCase;
    private final UpdateUserTypeUseCase updateUserTypeUseCase;
    private final FindByIdUserTypeUseCase findByIdUserTypeUseCase;
    private final FindListUserTypeUseCase findListUserTypeUseCase;
    private final DeactivateUserTypeUseCase deactivateUserTypeUseCase;
    private final ReactivateUserTypeUserCase reactivateUserTypeUserCase;
    private final com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper iUserTypeMapper;

    public UserTypeControllerInputPortImpl(
            CreateUserTypeUseCase createUserTypeUseCase,
            UpdateUserTypeUseCase updateUserTypeUseCase,
            FindByIdUserTypeUseCase findByIdUserTypeUseCase,
            FindListUserTypeUseCase findListUserTypeUseCase,
            DeactivateUserTypeUseCase deactivateUserTypeUseCase,
            ReactivateUserTypeUserCase reactivateUserTypeUserCase,
            IUserTypeMapper iUserTypeMapper
    ) {
        this.createUserTypeUseCase = createUserTypeUseCase;
        this.updateUserTypeUseCase = updateUserTypeUseCase;
        this.findByIdUserTypeUseCase = findByIdUserTypeUseCase;
        this.findListUserTypeUseCase = findListUserTypeUseCase;
        this.deactivateUserTypeUseCase = deactivateUserTypeUseCase;
        this.reactivateUserTypeUserCase = reactivateUserTypeUserCase;
        this.iUserTypeMapper = iUserTypeMapper;
    }

    @Override
    public UserTypeResponseDTO create(final UserTypeRequestDTO userTypeRequestDTO) {
        return this.createUserTypeUseCase.execute(userTypeRequestDTO);
    }

    @Override
    public UserTypeResponseDTO update(final UserTypeRequestDTO userTypeRequestDTO, Long id) {
        var userTypeModel = this.iUserTypeMapper.toUserType(userTypeRequestDTO, id);
        return this.updateUserTypeUseCase.execute(userTypeModel);
    }

    @Override
    public String toggleActivationUserType(Long id, boolean activate) {
        if (activate) {
            this.reactivateUserTypeUserCase.execute(id);
            return "User activated!";
        } else {
            this.deactivateUserTypeUseCase.execute(id);
            return "User deactivated!";
        }
    }

    @Override
    public UserTypeResponseDTO findById(final Long id) {
        var useType = this.findByIdUserTypeUseCase.execute(id);
        return this.iUserTypeMapper.toUserTypeResponseDto(useType);

    }

    @Override
    public List<UserTypeResponseDTO> findAllUserType(final int size, final int page) {
        var userTypes = this.findListUserTypeUseCase.execute(size,page);
        return this.iUserTypeMapper.toListUserTypeResponseDto(userTypes);
    }
}
