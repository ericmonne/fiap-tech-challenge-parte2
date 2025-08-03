package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateUserTypeUseCaseImplTest {

    private UserTypeGateway userTypeGateway;
    private IUserTypeMapper userTypeMapper;
    private CreateUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userTypeGateway = mock(UserTypeGateway.class);
        userTypeMapper = mock(IUserTypeMapper.class);
        useCase = new CreateUserTypeUseCaseImpl(userTypeGateway, userTypeMapper);
    }

    @Test
    void execute_shouldCreateUserTypeSuccessfully() {
        // Arrange
        Long generatedId = 1L;
        String name = "CUSTOMER";
        String description = "Regular customer user";
        
        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO(name, description);
        
        UserType userType = new UserType();
        userType.setId(generatedId);
        userType.setName(name);
        userType.setDescription(description);
        userType.setActive(true);
        
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO(
                generatedId, name, description, true);
        
        when(userTypeMapper.toUserType(requestDTO)).thenReturn(userType);
        when(userTypeGateway.createUserType(userType)).thenReturn(generatedId);
        when(userTypeGateway.findById(generatedId)).thenReturn(Optional.of(userType));
        when(userTypeMapper.toUserTypeResponseDto(userType)).thenReturn(responseDTO);
        
        // Act
        UserTypeResponseDTO result = useCase.execute(requestDTO);
        
        // Assert
        verify(userTypeMapper).toUserType(requestDTO);
        verify(userTypeGateway).createUserType(userType);
        verify(userTypeGateway).findById(generatedId);
        verify(userTypeMapper).toUserTypeResponseDto(userType);
        
        assertThat(result).isEqualTo(responseDTO);
        assertThat(result.id()).isEqualTo(generatedId);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.active()).isTrue();
    }
    
    @Test
    void execute_shouldThrowException_whenUserTypeNotFoundAfterCreation() {
        // Arrange
        Long generatedId = 1L;
        String name = "CUSTOMER";
        String description = "Regular customer user";
        
        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO(name, description);
        
        UserType userType = new UserType();
        userType.setName(name);
        userType.setDescription(description);
        
        when(userTypeMapper.toUserType(requestDTO)).thenReturn(userType);
        when(userTypeGateway.createUserType(userType)).thenReturn(generatedId);
        when(userTypeGateway.findById(generatedId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User type not created");
        
        verify(userTypeMapper).toUserType(requestDTO);
        verify(userTypeGateway).createUserType(userType);
        verify(userTypeGateway).findById(generatedId);
        verify(userTypeMapper, never()).toUserTypeResponseDto(any(UserType.class));
    }
    
    @Test
    void execute_shouldPropagateExceptions_fromDependencies() {
        // Arrange
        String name = "CUSTOMER";
        String description = "Regular customer user";
        
        UserTypeRequestDTO requestDTO = new UserTypeRequestDTO(name, description);
        
        // Mock mapper to throw exception
        when(userTypeMapper.toUserType(requestDTO)).thenThrow(new RuntimeException("Mapping error"));
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Mapping error");
        
        verify(userTypeMapper).toUserType(requestDTO);
        verify(userTypeGateway, never()).createUserType(any(UserType.class));
        verify(userTypeGateway, never()).findById(anyLong());
        verify(userTypeMapper, never()).toUserTypeResponseDto(any(UserType.class));
    }
}