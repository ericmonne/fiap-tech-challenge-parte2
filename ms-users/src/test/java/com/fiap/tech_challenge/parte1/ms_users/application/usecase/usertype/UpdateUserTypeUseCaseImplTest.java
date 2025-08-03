package com.fiap.tech_challenge.parte1.ms_users.application.usecase.usertype;

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

class UpdateUserTypeUseCaseImplTest {

    private UserTypeGateway userTypeGateway;
    private IUserTypeMapper userTypeMapper;
    private UpdateUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userTypeGateway = mock(UserTypeGateway.class);
        userTypeMapper = mock(IUserTypeMapper.class);
        useCase = new UpdateUserTypeUseCaseImpl(userTypeGateway, userTypeMapper);
    }

    @Test
    void execute_shouldUpdateUserTypeSuccessfully() {
        // Arrange
        Long id = 1L;
        String name = "CUSTOMER";
        String description = "Updated customer description";
        Boolean active = true;
        
        UserType userType = new UserType();
        userType.setId(id);
        userType.setName(name);
        userType.setDescription(description);
        userType.setActive(active);
        
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO(id, name, description, active);
        
        when(userTypeGateway.findById(id)).thenReturn(Optional.of(userType));
        when(userTypeMapper.toUserTypeResponseDto(userType)).thenReturn(responseDTO);
        
        // Act
        UserTypeResponseDTO result = useCase.execute(userType);
        
        // Assert
        verify(userTypeGateway, times(2)).findById(id); // Called before and after update
        verify(userTypeGateway).update(userType);
        verify(userTypeMapper).toUserTypeResponseDto(userType);
        
        assertThat(result).isEqualTo(responseDTO);
        assertThat(result.id()).isEqualTo(id);
        assertThat(result.name()).isEqualTo(name);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.active()).isEqualTo(active);
    }
    
    @Test
    void execute_shouldThrowException_whenUserTypeNotFoundBeforeUpdate() {
        // Arrange
        Long id = 1L;
        String name = "CUSTOMER";
        String description = "Updated customer description";
        Boolean active = true;
        
        UserType userType = new UserType();
        userType.setId(id);
        userType.setName(name);
        userType.setDescription(description);
        userType.setActive(active);
        
        when(userTypeGateway.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(userType))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User type not found");
        
        verify(userTypeGateway).findById(id);
        verify(userTypeGateway, never()).update(any(UserType.class));
        verify(userTypeMapper, never()).toUserTypeResponseDto(any(UserType.class));
    }
    
    @Test
    void execute_shouldThrowException_whenUserTypeNotFoundAfterUpdate() {
        // Arrange
        Long id = 1L;
        String name = "CUSTOMER";
        String description = "Updated customer description";
        Boolean active = true;
        
        UserType userType = new UserType();
        userType.setId(id);
        userType.setName(name);
        userType.setDescription(description);
        userType.setActive(active);
        
        // First findById returns the user type, second findById returns empty
        when(userTypeGateway.findById(id))
                .thenReturn(Optional.of(userType))
                .thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(userType))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User type not found");
        
        verify(userTypeGateway, times(2)).findById(id);
        verify(userTypeGateway).update(userType);
        verify(userTypeMapper, never()).toUserTypeResponseDto(any(UserType.class));
    }
    
    @Test
    void execute_shouldPropagateExceptions_fromDependencies() {
        // Arrange
        Long id = 1L;
        UserType userType = new UserType();
        userType.setId(id);
        
        // Mock gateway to throw exception
        when(userTypeGateway.findById(id)).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(userType))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database error");
        
        verify(userTypeGateway).findById(id);
        verify(userTypeGateway, never()).update(any(UserType.class));
        verify(userTypeMapper, never()).toUserTypeResponseDto(any(UserType.class));
    }
}