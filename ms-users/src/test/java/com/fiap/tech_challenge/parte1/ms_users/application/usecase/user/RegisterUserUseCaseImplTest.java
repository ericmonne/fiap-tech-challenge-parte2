package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.CreateUserDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UsersResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.user.IUserMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.token.TokenProvider;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RegisterUserUseCaseImplTest {

    private UserGateway userGateway;
    private UserTypeGateway userTypeGateway;
    private AddressGateway addressGateway;
    private PasswordEncoder passwordEncoder;
    private TokenProvider tokenProvider;
    private IUserMapper userMapper;
    private List<UserValidator> userValidators;
    private RegisterUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        userTypeGateway = mock(UserTypeGateway.class);
        addressGateway = mock(AddressGateway.class);
        passwordEncoder = mock(PasswordEncoder.class);
        tokenProvider = mock(TokenProvider.class);
        userMapper = mock(IUserMapper.class);
        userValidators = new ArrayList<>();
        
        // Add a mock validator
        UserValidator validator = mock(UserValidator.class);
        userValidators.add(validator);
        
        useCase = new RegisterUserUseCaseImpl(
                userGateway,
                userTypeGateway,
                addressGateway,
                passwordEncoder,
                tokenProvider,
                userMapper,
                userValidators
        );
    }

    @Test
    void execute_shouldRegisterUserSuccessfully() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";
        String token = "jwt.token.here";
        
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        user.setPassword(rawPassword);
        
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setName("CUSTOMER");
        userType.setDescription("Regular customer");
        userType.setActive(true);
        user.setUserType(userType);
        
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setStreet("Main Street");
        address.setNumber(123);
        address.setCity("New York");
        address.setState("NY");
        address.setZipcode("12345-678");
        addresses.add(address);
        user.setAddress(addresses);
        
        UsersResponseDTO responseDTO = mock(UsersResponseDTO.class);
        
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userTypeGateway.findByName("CUSTOMER")).thenReturn(Optional.of(userType));
        when(userGateway.createUser(any(User.class))).thenReturn(userId);
        when(userGateway.findById(userId)).thenReturn(Optional.of(user));
        when(addressGateway.findAllByUserId(userId)).thenReturn(addresses);
        when(userMapper.toResponseDTO(any(User.class))).thenReturn(responseDTO);
        when(tokenProvider.generateToken(anyString())).thenReturn(token);
        
        // Act
        CreateUserDTO result = useCase.execute(user);
        
        // Assert
        verify(userValidators.get(0)).validate(user);
        verify(passwordEncoder).encode(rawPassword);
        verify(userTypeGateway).findByName("CUSTOMER");
        
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userGateway).createUser(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getPassword()).isEqualTo(encodedPassword);
        
        verify(addressGateway).saveUserAddress(eq(addresses), eq(userId));
        verify(userGateway).findById(userId);
        verify(addressGateway).findAllByUserId(userId);
        verify(userMapper).toResponseDTO(user);
        verify(tokenProvider).generateToken(user.getLogin());
        
        assertThat(result.user()).isEqualTo(responseDTO);
        assertThat(result.tokenJWT()).isEqualTo(token);
    }
    
    @Test
    void execute_shouldThrowException_whenUserTypeNotFound() {
        // Arrange
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";
        
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        user.setPassword(rawPassword);
        
        UserType userType = new UserType();
        userType.setName("NONEXISTENT_TYPE");
        user.setUserType(userType);
        
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userTypeGateway.findByName("NONEXISTENT_TYPE")).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(user))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User type NONEXISTENT_TYPE not found");
        
        verify(userValidators.get(0)).validate(user);
        verify(passwordEncoder).encode(rawPassword);
        verify(userTypeGateway).findByName("NONEXISTENT_TYPE");
        verify(userGateway, never()).createUser(any(User.class));
    }
    
    @Test
    void execute_shouldThrowException_whenUserNotFoundAfterCreation() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";
        
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        user.setPassword(rawPassword);
        
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setName("CUSTOMER");
        userType.setDescription("Regular customer");
        userType.setActive(true);
        user.setUserType(userType);
        
        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setStreet("Main Street");
        address.setNumber(123);
        address.setCity("New York");
        address.setState("NY");
        address.setZipcode("12345-678");
        addresses.add(address);
        user.setAddress(addresses);
        
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userTypeGateway.findByName("CUSTOMER")).thenReturn(Optional.of(userType));
        when(userGateway.createUser(any(User.class))).thenReturn(userId);
        when(userGateway.findById(userId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(user))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id " + userId + " not found");
        
        verify(userValidators.get(0)).validate(user);
        verify(passwordEncoder).encode(rawPassword);
        verify(userTypeGateway).findByName("CUSTOMER");
        verify(userGateway).createUser(any(User.class));
        verify(addressGateway).saveUserAddress(eq(addresses), eq(userId));
        verify(userGateway).findById(userId);
        verify(addressGateway, never()).findAllByUserId(any(UUID.class));
    }
    
    @Test
    void execute_shouldPropagateExceptions_fromDependencies() {
        // Arrange
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        user.setPassword("password123");
        
        UserType userType = new UserType();
        userType.setName("CUSTOMER");
        user.setUserType(userType);
        
        // Mock validator to throw exception
        doThrow(new RuntimeException("Validation error")).when(userValidators.get(0)).validate(user);
        
        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(user))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Validation error");
        
        verify(userValidators.get(0)).validate(user);
        verify(passwordEncoder, never()).encode(anyString());
        verify(userTypeGateway, never()).findByName(anyString());
        verify(userGateway, never()).createUser(any(User.class));
    }
}