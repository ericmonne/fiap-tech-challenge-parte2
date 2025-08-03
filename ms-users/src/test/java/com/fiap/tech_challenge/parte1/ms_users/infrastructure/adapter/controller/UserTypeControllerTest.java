package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.UserTypeControllerInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserTypeControllerTest {

    private UserTypeControllerInputPort userTypeControllerInputPort;
    private UserTypeController userTypeController;

    @BeforeEach
    void setUp() {
        userTypeControllerInputPort = mock(UserTypeControllerInputPort.class);
        userTypeController = new UserTypeController(userTypeControllerInputPort);
    }

    @Test
    void createUserType_shouldReturnCreatedUserType() {
        // Arrange
        UserTypeRequestDTO requestDTO = mock(UserTypeRequestDTO.class);
        UserTypeResponseDTO responseDTO = mock(UserTypeResponseDTO.class);
        when(userTypeControllerInputPort.create(requestDTO)).thenReturn(responseDTO);

        // Act
        ResponseEntity<UserTypeResponseDTO> response = userTypeController.createUserType(requestDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(responseDTO);
        verify(userTypeControllerInputPort).create(requestDTO);
    }

    @Test
    void toggleActivationUserType_shouldReturnMessage_whenActivating() {
        // Arrange
        Long userTypeId = 1L;
        boolean activate = true;
        String message = "User type activated successfully";
        when(userTypeControllerInputPort.toggleActivationUserType(userTypeId, activate)).thenReturn(message);

        // Act
        ResponseEntity<String> response = userTypeController.toggleActivationUserType(userTypeId, activate);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(message);
        verify(userTypeControllerInputPort).toggleActivationUserType(userTypeId, activate);
    }

    @Test
    void toggleActivationUserType_shouldReturnMessage_whenDeactivating() {
        // Arrange
        Long userTypeId = 1L;
        boolean activate = false;
        String message = "User type deactivated successfully";
        when(userTypeControllerInputPort.toggleActivationUserType(userTypeId, activate)).thenReturn(message);

        // Act
        ResponseEntity<String> response = userTypeController.toggleActivationUserType(userTypeId, activate);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(message);
        verify(userTypeControllerInputPort).toggleActivationUserType(userTypeId, activate);
    }

    @Test
    void updateUserType_shouldReturnUpdatedUserType() {
        // Arrange
        Long userTypeId = 1L;
        UserTypeRequestDTO requestDTO = mock(UserTypeRequestDTO.class);
        UserTypeResponseDTO responseDTO = mock(UserTypeResponseDTO.class);
        when(userTypeControllerInputPort.update(requestDTO, userTypeId)).thenReturn(responseDTO);

        // Act
        ResponseEntity<UserTypeResponseDTO> response = userTypeController.updateUserType(requestDTO, userTypeId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(response.getBody()).isEqualTo(responseDTO);
        verify(userTypeControllerInputPort).update(requestDTO, userTypeId);
    }

    @Test
    void findByIdUserType_shouldReturnUserType() {
        // Arrange
        Long userTypeId = 1L;
        UserTypeResponseDTO responseDTO = mock(UserTypeResponseDTO.class);
        when(userTypeControllerInputPort.findById(userTypeId)).thenReturn(responseDTO);

        // Act
        ResponseEntity<UserTypeResponseDTO> response = userTypeController.findByIdUserType(userTypeId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(responseDTO);
        verify(userTypeControllerInputPort).findById(userTypeId);
    }

    @Test
    void findAllUserType_shouldReturnUserTypesList() {
        // Arrange
        int size = 10;
        int offset = 0;
        List<UserTypeResponseDTO> userTypesList = new ArrayList<>();
        when(userTypeControllerInputPort.findAllUserType(size, offset)).thenReturn(userTypesList);

        // Act
        ResponseEntity<List<UserTypeResponseDTO>> response = userTypeController.findAllUserType(size, offset);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userTypesList);
        verify(userTypeControllerInputPort).findAllUserType(size, offset);
    }
}