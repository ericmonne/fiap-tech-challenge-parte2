package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.*;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller.UsersControllerInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UsersControllerTest {

    private UsersControllerInputPort usersControllerInputPort;
    private UsersController usersController;

    @BeforeEach
    void setUp() {
        usersControllerInputPort = mock(UsersControllerInputPort.class);
        usersController = new UsersController(usersControllerInputPort);
    }

    @Test
    void getById_shouldReturnUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UsersResponseDTO responseDTO = mock(UsersResponseDTO.class);
        when(usersControllerInputPort.getById(userId)).thenReturn(responseDTO);

        // Act
        ResponseEntity<UsersResponseDTO> response = usersController.getById(userId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(responseDTO);
        verify(usersControllerInputPort).getById(userId);
    }

    @Test
    void findAllUsers_shouldReturnUsersList() {
        // Arrange
        int size = 10;
        int page = 0;
        List<UsersResponseDTO> usersList = new ArrayList<>();
        when(usersControllerInputPort.findAllUsers(size, page)).thenReturn(usersList);

        // Act
        ResponseEntity<List<UsersResponseDTO>> response = usersController.findAllUsers(size, page);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(usersList);
        verify(usersControllerInputPort).findAllUsers(size, page);
    }

    @Test
    void create_shouldReturnCreatedUser() {
        // Arrange
        UsersRequestDTO requestDTO = mock(UsersRequestDTO.class);
        CreateUserDTO createUserDTO = mock(CreateUserDTO.class);
        when(usersControllerInputPort.create(requestDTO)).thenReturn(createUserDTO);

        // Act
        ResponseEntity<CreateUserDTO> response = usersController.create(requestDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(createUserDTO);
        verify(usersControllerInputPort).create(requestDTO);
    }

    @Test
    void executeLogin_shouldReturnToken() {
        // Arrange
        AuthenticationDataDTO authData = mock(AuthenticationDataDTO.class);
        TokenJWTInfoDTO tokenInfo = mock(TokenJWTInfoDTO.class);
        when(usersControllerInputPort.executeLogin(authData)).thenReturn(tokenInfo);

        // Act
        ResponseEntity<TokenJWTInfoDTO> response = usersController.executeLogin(authData);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(tokenInfo);
        verify(usersControllerInputPort).executeLogin(authData);
    }

    @Test
    void toggleActivation_shouldReturnMessage_whenActivating() {
        // Arrange
        UUID userId = UUID.randomUUID();
        boolean activate = true;
        String message = "User activated successfully";
        when(usersControllerInputPort.toggleActivation(userId, activate)).thenReturn(message);

        // Act
        ResponseEntity<String> response = usersController.toggleActivation(userId, activate);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(message);
        verify(usersControllerInputPort).toggleActivation(userId, activate);
    }

    @Test
    void toggleActivation_shouldReturnMessage_whenDeactivating() {
        // Arrange
        UUID userId = UUID.randomUUID();
        boolean activate = false;
        String message = "User deactivated successfully";
        when(usersControllerInputPort.toggleActivation(userId, activate)).thenReturn(message);

        // Act
        ResponseEntity<String> response = usersController.toggleActivation(userId, activate);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(message);
        verify(usersControllerInputPort).toggleActivation(userId, activate);
    }

    @Test
    void changePassword_shouldReturnMessage() {
        // Arrange
        UUID userId = UUID.randomUUID();
        ChangePasswordRequestDTO requestDTO = mock(ChangePasswordRequestDTO.class);
        String message = "Password changed successfully";
        when(usersControllerInputPort.changePassword(userId, requestDTO)).thenReturn(message);

        // Act
        ResponseEntity<String> response = usersController.changePassword(userId, requestDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(message);
        verify(usersControllerInputPort).changePassword(userId, requestDTO);
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UpdateUserDTO requestDTO = mock(UpdateUserDTO.class);
        UsersResponseDTO responseDTO = mock(UsersResponseDTO.class);
        when(usersControllerInputPort.updateUser(userId, requestDTO)).thenReturn(responseDTO);

        // Act
        ResponseEntity<UsersResponseDTO> response = usersController.updateUser(userId, requestDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(responseDTO);
        verify(usersControllerInputPort).updateUser(userId, requestDTO);
    }
}