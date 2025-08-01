package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.api.routes.UserTypeRoutes;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.UserTypeControllerInputPort;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> createUserType(
            @RequestParam final @Valid UserTypeRequestDTO userTypeRequestDTO
    ) {
        this.userTypeControllerInputPort.create(userTypeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/activation")
    public ResponseEntity<String> toggleActivationUserType(
            @PathVariable final Long id,
            @RequestParam final boolean activate
    ) {
        return ResponseEntity.ok(userTypeControllerInputPort.toggleActivationUserType(id, activate));
    }

    @PutMapping
    public ResponseEntity<Void> updateUserType(
            @RequestParam final @Valid UserTypeRequestDTO userTypeRequestDTO
    ) {
        this.userTypeControllerInputPort.update(userTypeRequestDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponseDTO> findByIdUserType(
            @PathVariable("id") final Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userTypeControllerInputPort.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserTypeResponseDTO>> findAllUserType(
            @RequestParam final int size,
            @RequestParam final int offset
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userTypeControllerInputPort.findAllUserType(size,offset));
    }
}
