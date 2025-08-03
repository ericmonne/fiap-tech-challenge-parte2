package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype.controller.UserTypeControllerInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTypeControllerTest {

    @Mock
    private UserTypeControllerInputPort inputPort;

    @InjectMocks
    private UserTypeController controller;

    private UserTypeRequestDTO requestDTO;
    private UserTypeResponseDTO responseDTO;
    private final Long userId = 1L;

    @BeforeEach
    void setUp() {
        requestDTO = new UserTypeRequestDTO("Admin", "Administrator");
        responseDTO = new UserTypeResponseDTO(userId, "Admin", "Administrator", true);
    }

    @Test
    void createUserType_ShouldReturnCreatedStatus() {
        when(inputPort.create(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<UserTypeResponseDTO> response = controller.createUserType(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(inputPort).create(requestDTO);
    }

    @Test
    void toggleActivationUserType_ShouldReturnOkStatus() {
        String expectedMessage = "User activated!";
        when(inputPort.toggleActivationUserType(userId, true)).thenReturn(expectedMessage);

        ResponseEntity<String> response = controller.toggleActivationUserType(userId, true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
        verify(inputPort).toggleActivationUserType(userId, true);
    }

    @Test
    void updateUserType_ShouldReturnAcceptedStatus() {
        when(inputPort.update(requestDTO, userId)).thenReturn(responseDTO);

        ResponseEntity<UserTypeResponseDTO> response = controller.updateUserType(requestDTO, userId);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(inputPort).update(requestDTO, userId);
    }

    @Test
    void findByIdUserType_ShouldReturnOkStatus() {
        when(inputPort.findById(userId)).thenReturn(responseDTO);

        ResponseEntity<UserTypeResponseDTO> response = controller.findByIdUserType(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(inputPort).findById(userId);
    }

    @Test
    void findAllUserType_ShouldReturnOkStatus() {
        List<UserTypeResponseDTO> responseList = List.of(responseDTO);
        when(inputPort.findAllUserType(10, 0)).thenReturn(responseList);

        ResponseEntity<List<UserTypeResponseDTO>> response = controller.findAllUserType(10, 0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseList, response.getBody());
        verify(inputPort).findAllUserType(10, 0);
    }

    @Test
    void findAllUserType_ShouldValidateParameters() {
        List<UserTypeResponseDTO> responseList = List.of(responseDTO);
        when(inputPort.findAllUserType(5, 1)).thenReturn(responseList);

        ResponseEntity<List<UserTypeResponseDTO>> response = controller.findAllUserType(5, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(inputPort).findAllUserType(5, 1);
    }

    @Test
    void toggleActivationUserType_ShouldHandleDeactivation() {
        String expectedMessage = "User deactivated!";
        when(inputPort.toggleActivationUserType(userId, false)).thenReturn(expectedMessage);

        ResponseEntity<String> response = controller.toggleActivationUserType(userId, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
        verify(inputPort).toggleActivationUserType(userId, false);
    }
}