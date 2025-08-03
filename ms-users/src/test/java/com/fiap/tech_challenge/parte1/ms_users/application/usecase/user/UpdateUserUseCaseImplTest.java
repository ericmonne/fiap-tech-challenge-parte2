package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.EmailAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.LoginAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseImplTest {

    private UserGateway userGateway;
    private AddressGateway addressGateway;
    private UpdateUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        addressGateway = mock(AddressGateway.class);
        useCase = new UpdateUserUseCaseImpl(userGateway, addressGateway);
    }

    @Test
    void execute_shouldUpdateUserSuccessfully() {
        // Arrange
        UUID userId = UUID.randomUUID();
        
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        user.setPassword("password123");
        user.setActive(true);
        
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setName("CUSTOMER");
        user.setUserType(userType);
        
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setUserId(userId);
        address.setStreet("Main Street");
        address.setNumber(123);
        address.setCity("New York");
        address.setState("NY");
        address.setZipcode("12345-678");
        addresses.add(address);
        user.setAddress(addresses);
        
        when(userGateway.existsById(userId)).thenReturn(true);
        when(userGateway.emailAlreadyExistsForDifferentUsers(user.getEmail(), userId)).thenReturn(false);
        when(userGateway.loginAlreadyExistsForDifferentUsers(user.getLogin(), userId)).thenReturn(false);
        when(userGateway.findById(userId)).thenReturn(Optional.of(user));
        
        // Act
        User result = useCase.execute(user);
        
        // Assert
        verify(userGateway).existsById(userId);
        verify(userGateway).emailAlreadyExistsForDifferentUsers(user.getEmail(), userId);
        verify(userGateway).loginAlreadyExistsForDifferentUsers(user.getLogin(), userId);
        verify(userGateway).update(user);
        verify(addressGateway).updateUserAddress(eq(addresses), eq(userId));
        verify(userGateway).findById(userId);
        
        assertThat(result).isEqualTo(user);
    }
    
    @Test
    void execute_shouldUpdateUserWithoutAddresses() {
        // Arrange
        UUID userId = UUID.randomUUID();
        
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        user.setPassword("password123");
        user.setActive(true);
        
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setName("CUSTOMER");
        user.setUserType(userType);
        
        // No addresses
        user.setAddress(null);
        
        when(userGateway.existsById(userId)).thenReturn(true);
        when(userGateway.emailAlreadyExistsForDifferentUsers(user.getEmail(), userId)).thenReturn(false);
        when(userGateway.loginAlreadyExistsForDifferentUsers(user.getLogin(), userId)).thenReturn(false);
        when(userGateway.findById(userId)).thenReturn(Optional.of(user));
        
        // Act
        User result = useCase.execute(user);
        
        // Assert
        verify(userGateway).existsById(userId);
        verify(userGateway).emailAlreadyExistsForDifferentUsers(user.getEmail(), userId);
        verify(userGateway).loginAlreadyExistsForDifferentUsers(user.getLogin(), userId);
        verify(userGateway).update(user);
        verify(addressGateway, never()).updateUserAddress(any(), any());
        verify(userGateway).findById(userId);
        
        assertThat(result).isEqualTo(user);
    }
    
    @Test
    void execute_shouldThrowException_whenUserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        
        when(userGateway.existsById(userId)).thenReturn(false);
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(user))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id " + userId + " not found");
        
        verify(userGateway).existsById(userId);
        verify(userGateway, never()).emailAlreadyExistsForDifferentUsers(anyString(), any(UUID.class));
        verify(userGateway, never()).loginAlreadyExistsForDifferentUsers(anyString(), any(UUID.class));
        verify(userGateway, never()).update(any(User.class));
        verify(addressGateway, never()).updateUserAddress(any(), any());
    }
    
    @Test
    void execute_shouldThrowException_whenEmailAlreadyExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        
        when(userGateway.existsById(userId)).thenReturn(true);
        when(userGateway.emailAlreadyExistsForDifferentUsers(user.getEmail(), userId)).thenReturn(true);
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(user))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("O e-mail informado já está em uso");
        
        verify(userGateway).existsById(userId);
        verify(userGateway).emailAlreadyExistsForDifferentUsers(user.getEmail(), userId);
        verify(userGateway, never()).loginAlreadyExistsForDifferentUsers(anyString(), any(UUID.class));
        verify(userGateway, never()).update(any(User.class));
        verify(addressGateway, never()).updateUserAddress(any(), any());
    }
    
    @Test
    void execute_shouldThrowException_whenLoginAlreadyExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        
        when(userGateway.existsById(userId)).thenReturn(true);
        when(userGateway.emailAlreadyExistsForDifferentUsers(user.getEmail(), userId)).thenReturn(false);
        when(userGateway.loginAlreadyExistsForDifferentUsers(user.getLogin(), userId)).thenReturn(true);
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(user))
                .isInstanceOf(LoginAlreadyExistsException.class)
                .hasMessageContaining("O login informado já está em uso");
        
        verify(userGateway).existsById(userId);
        verify(userGateway).emailAlreadyExistsForDifferentUsers(user.getEmail(), userId);
        verify(userGateway).loginAlreadyExistsForDifferentUsers(user.getLogin(), userId);
        verify(userGateway, never()).update(any(User.class));
        verify(addressGateway, never()).updateUserAddress(any(), any());
    }
    
    @Test
    void execute_shouldThrowException_whenUserNotFoundAfterUpdate() {
        // Arrange
        UUID userId = UUID.randomUUID();
        
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        
        when(userGateway.existsById(userId)).thenReturn(true);
        when(userGateway.emailAlreadyExistsForDifferentUsers(user.getEmail(), userId)).thenReturn(false);
        when(userGateway.loginAlreadyExistsForDifferentUsers(user.getLogin(), userId)).thenReturn(false);
        when(userGateway.findById(userId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(user))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id " + userId + " not found");
        
        verify(userGateway).existsById(userId);
        verify(userGateway).emailAlreadyExistsForDifferentUsers(user.getEmail(), userId);
        verify(userGateway).loginAlreadyExistsForDifferentUsers(user.getLogin(), userId);
        verify(userGateway).update(user);
        verify(userGateway).findById(userId);
    }
}