package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class FindByIdUserUseCaseImplTest {

    private UserGateway userGateway;
    private AddressGateway addressGateway;
    private FindByIdUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        addressGateway = mock(AddressGateway.class);
        useCase = new FindByIdUserUseCaseImpl(userGateway, addressGateway);
    }

    @Test
    void execute_shouldReturnUserWithAddresses_whenUserExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setLogin("testuser");
        user.setPassword("password");
        user.setActive(true);
        user.setUserType(new UserType());

        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setUserId(userId);
        address.setStreet("Test Street");
        address.setNumber(123);
        address.setCity("Test City");
        address.setState("TS");
        address.setZipcode("12345-678");
        addresses.add(address);

        when(userGateway.findById(userId)).thenReturn(Optional.of(user));
        when(addressGateway.findAllByUserId(userId)).thenReturn(addresses);

        // Act
        User result = useCase.execute(userId);

        // Assert
        assertThat(result).isEqualTo(user);
        assertThat(result.getAddresses()).isEqualTo(addresses);
        verify(userGateway).findById(userId);
        verify(addressGateway).findAllByUserId(userId);
    }

    @Test
    void execute_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(userId.toString());

        verify(userGateway).findById(userId);
        verifyNoInteractions(addressGateway);
    }

    @Test
    void execute_shouldPropagateRuntimeException_whenUserGatewayThrowsUnexpectedError() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userGateway.findById(userId)).thenThrow(new RuntimeException("Unexpected gateway failure"));

        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unexpected gateway failure");

        verify(userGateway).findById(userId);
        verifyNoInteractions(addressGateway);
    }

    @Test
    void execute_shouldPropagateRuntimeException_whenAddressGatewayThrowsUnexpectedError() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userGateway.findById(userId)).thenReturn(Optional.of(user));
        when(addressGateway.findAllByUserId(userId)).thenThrow(new RuntimeException("Address gateway failure"));

        // Act & Assert
        assertThatThrownBy(() -> useCase.execute(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Address gateway failure");

        verify(userGateway).findById(userId);
        verify(addressGateway).findAllByUserId(userId);
    }
}