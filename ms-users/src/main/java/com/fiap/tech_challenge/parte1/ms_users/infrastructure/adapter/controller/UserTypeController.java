package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.api.routes.UserTypeRoutes;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.UserTypeControllerInputPort;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserTypeRoutes.USER_TYPES_BASE)
public class UserTypeController {

    private final UserTypeControllerInputPort userTypeControllerInputPort;

    public UserTypeController(final UserTypeControllerInputPort userTypeControllerInputPort) {
        this.userTypeControllerInputPort = userTypeControllerInputPort;
    }

    @PostMapping
    public ResponseEntity<UserTypeResponseDTO> createUserType(
            @RequestBody @Valid UserTypeRequestDTO userTypeRequestDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userTypeControllerInputPort.create(userTypeRequestDTO));
    }

    @PatchMapping("/{id}/activation")
    public ResponseEntity<String> toggleActivationUserType(
            @PathVariable final Long id,
            @RequestParam final boolean activate
    ) {
        return ResponseEntity.ok(userTypeControllerInputPort.toggleActivationUserType(id, activate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTypeResponseDTO> updateUserType(
            @RequestBody final @Valid UserTypeRequestDTO userTypeRequestDTO,
            @PathVariable final Long id
    ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userTypeControllerInputPort.update(userTypeRequestDTO, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponseDTO> findByIdUserType(
            @PathVariable("id") final Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userTypeControllerInputPort.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserTypeResponseDTO>> findAllUserType(
            @Valid @Min(1) @RequestParam final int size,
            @Valid @Min(1) @RequestParam final int offset
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userTypeControllerInputPort.findAllUserType(size, offset));
    }
}
