package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.CreateUserDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UpdateUserDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UsersRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UsersResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller.UsersControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.openapi.UsersApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing users.
 * <p>
 * Provides endpoints to create, update, retrieve, activate/deactivate users,
 * and handle authentication-related operations.
 * </p>
 */
@RestController
public class UsersController implements UsersApi {

    private final UsersControllerInputPort UsersControllerInputPort;

    public UsersController(UsersControllerInputPort usersControllerInputPort) {
        UsersControllerInputPort = usersControllerInputPort;
    }

    @Override
    public ResponseEntity<UsersResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(UsersControllerInputPort.getById(id));
    }

    @Override
    public ResponseEntity<List<UsersResponseDTO>> findAllUsers(
            @RequestParam int size,
            @RequestParam int page
    ) {
        return ResponseEntity.ok(UsersControllerInputPort.findAllUsers(size, page));
    }

    @Override
    public ResponseEntity<CreateUserDTO> create(@RequestBody @Valid UsersRequestDTO dto) {
        return ResponseEntity.ok(UsersControllerInputPort.create(dto));
    }

    @Override
    public ResponseEntity<TokenJWTInfoDTO> executeLogin(@RequestBody @Valid AuthenticationDataDTO data) {
        return ResponseEntity.ok(UsersControllerInputPort.executeLogin(data));
    }

    @Override
    public ResponseEntity<String> toggleActivation(
            @PathVariable UUID id,
            @RequestParam boolean activate
    ) {
        return ResponseEntity.ok(UsersControllerInputPort.toggleActivation(id, activate));
    }

    @Override
    public ResponseEntity<String> changePassword(
            @PathVariable UUID id,
            @RequestBody @Valid ChangePasswordRequestDTO dto
    ) {
        return ResponseEntity.ok(UsersControllerInputPort.changePassword(id, dto));
    }

    @Override
    public ResponseEntity<UsersResponseDTO> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateUserDTO dto) {
        return ResponseEntity.ok(UsersControllerInputPort.updateUser(id, dto));
    }

}
