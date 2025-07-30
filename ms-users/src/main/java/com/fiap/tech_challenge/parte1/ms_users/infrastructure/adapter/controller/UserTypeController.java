package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.UserTypeControllerInputPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usertypes")
public class UserTypeController {

    private final UserTypeControllerInputPort userTypeControllerInputPort;

    public UserTypeController(UserTypeControllerInputPort userTypeControllerInputPort) {
        this.userTypeControllerInputPort = userTypeControllerInputPort;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(
            @RequestParam UserTypeRequestDTO userTypeRequestDTO
    ) {
        this.userTypeControllerInputPort.create(userTypeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
