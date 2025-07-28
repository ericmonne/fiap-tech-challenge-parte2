package com.fiap.tech_challenge.parte1.ms_users.application.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller.UsersControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.user.IUserMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class UsersControllerInputPortImpl implements UsersControllerInputPort {

    private static final Logger logger = LoggerFactory.getLogger(UsersControllerInputPortImpl.class);

    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final FindListUserUseCase findListUserUseCase;
    private final FindByIdUserUseCase findByIdUserUseCase;
    private final DeactivateUserUseCase deactivateUserUseCase;
    private final ReactivateUserUseCase reactivateUserUseCase;
    private final ChangePasswordUserUseCase changePasswordUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final IUserMapper iUserMapper;

    public UsersControllerInputPortImpl(RegisterUserUseCase registerUserUseCase, UpdateUserUseCase updateUserUseCase, FindListUserUseCase findListUserUseCase, FindByIdUserUseCase findByIdUserUseCase, DeactivateUserUseCase deactivateUserUseCase, ReactivateUserUseCase reactivateUserUseCase, ChangePasswordUserUseCase changePasswordUserUseCase, AuthenticateUserUseCase authenticateUserUseCase, IUserMapper iUserMapper) {
        this.registerUserUseCase = registerUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.findListUserUseCase = findListUserUseCase;
        this.findByIdUserUseCase = findByIdUserUseCase;
        this.deactivateUserUseCase = deactivateUserUseCase;
        this.reactivateUserUseCase = reactivateUserUseCase;
        this.changePasswordUserUseCase = changePasswordUserUseCase;
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.iUserMapper = iUserMapper;
    }

    @Override
    public UsersResponseDTO getById(UUID id) {
        logger.info("/getById -> id: {}", id);
        User userEntity = findByIdUserUseCase.execute(id);
        return iUserMapper.toResponseDTO(userEntity);
    }

    @Override
    public List<UsersResponseDTO> findAllUsers(int size, int page) {
        logger.info("/findAllUsers -> size: {} ,  offset: {}", size, page);
        List<User> allUsers = findListUserUseCase.execute(size, page);
        return iUserMapper.toResponseDTO(allUsers);
    }

    @Override
    public CreateUserDTO create(UsersRequestDTO dto) {
        logger.info("/createUser -> {}", dto);
        User userEntity = iUserMapper.toEntity(dto);
        return registerUserUseCase.execute(userEntity);
    }

    @Override
    public TokenJWTInfoDTO executeLogin(AuthenticationDataDTO data) {
        logger.info("/login -> {}", data);
        return authenticateUserUseCase.execute(data);
    }

    @Override
    public String toggleActivation(UUID id, boolean activate) {
        logger.info("/toggleActivation -> id: {}, activate: {}", id, activate);
        if (activate) {
            reactivateUserUseCase.execute(id);
            return "User activated!";
        } else {
            deactivateUserUseCase.execute(id);
            return "User deactivated!";
        }
    }

    @Override
    public String changePassword(UUID id, ChangePasswordRequestDTO dto) {
        logger.info("/changePassword -> id: {}", id);
        changePasswordUserUseCase.execute(id, new ChangePasswordCommand(dto.oldPassword(), dto.newPassword()));
        return "Password updated successfully!";
    }

    @Override
    public UsersResponseDTO updateUser(UUID id, UpdateUserDTO dto) {
        logger.info("/updateUser -> id: {}, body: {}", id, dto);
        User userEntity = iUserMapper.toEntity(dto, id);
        User userAfterUpdate = updateUserUseCase.execute(userEntity);
        return iUserMapper.toResponseDTO(userAfterUpdate);
    }
}
