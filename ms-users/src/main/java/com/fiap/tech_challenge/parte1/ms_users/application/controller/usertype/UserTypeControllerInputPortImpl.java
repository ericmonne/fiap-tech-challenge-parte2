package com.fiap.tech_challenge.parte1.ms_users.application.controller.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.controller.user.UsersControllerInputPortImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.IUserTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class UserTypeControllerInputPortImpl implements UserTypeControllerInputPort {

    private static final Logger logger = LoggerFactory.getLogger(UsersControllerInputPortImpl.class);

    private final CreateUserTypeUseCase createUserTypeUseCase;
    private final UpdateUserTypeUseCase updateUserTypeUseCase;
    private final FindByIdUserTypeUseCase findByIdUserTypeUseCase;
    private final FindListUserTypeUseCase findListUserTypeUseCase;
    private final DeactivateUserTypeUseCase deactivateUserTypeUseCase;
    private final ReactivateUserTypeUserCase reactivateUserTypeUserCase;
    private final IUserTypeMapper iUserTypeMapper;

    public UserTypeControllerInputPortImpl(
            final CreateUserTypeUseCase createUserTypeUseCase,
            final UpdateUserTypeUseCase updateUserTypeUseCase,
            final FindByIdUserTypeUseCase findByIdUserTypeUseCase,
            final FindListUserTypeUseCase findListUserTypeUseCase,
            final DeactivateUserTypeUseCase deactivateUserTypeUseCase,
            final ReactivateUserTypeUserCase reactivateUserTypeUserCase,
            final IUserTypeMapper iUserTypeMapper
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
    public void create(final UserTypeRequestDTO userTypeRequestDTO) {
        this.createUserTypeUseCase.execute(userTypeRequestDTO);
    }

    @Override
    public void update(final UserTypeRequestDTO userTypeRequestDTO) {
        var userTypeModel = this.iUserTypeMapper.toUserType(userTypeRequestDTO);
        this.updateUserTypeUseCase.execute(userTypeModel);
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
    public UserTypeResponseDTO getById(final Long id) {
        var useType = this.findByIdUserTypeUseCase.execute(id);
        return this.iUserTypeMapper.toUserTypeResponseDto(useType);

    }

    @Override
    public List<UserTypeResponseDTO> findAllUserType(final int size, final int page) {
        var userTypes = this.findListUserTypeUseCase.execute(size,page);
        return this.iUserTypeMapper.toListUserTypeResponseDto(userTypes);
    }
}
