package com.fiap.tech_challenge.parte1.ms_users.infrastructure.validators;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.LoginAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginValidatorTest {

    private UserGateway userGateway;
    private LoginValidator validator;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        validator = new LoginValidator(userGateway);
    }

    @Test
    void shouldPassWhenLoginDoesNotExist() {
        User user = new User();
        user.setLogin("newUser");

        when(userGateway.existsByLogin("newUser")).thenReturn(false);

        assertDoesNotThrow(() -> validator.validate(user));
        verify(userGateway).existsByLogin("newUser");
    }

    @Test
    void shouldThrowExceptionWhenLoginExists() {
        User user = new User();
        user.setLogin("existingUser");

        when(userGateway.existsByLogin("existingUser")).thenReturn(true);

        LoginAlreadyExistsException exception = assertThrows(LoginAlreadyExistsException.class, () -> validator.validate(user));

        assertEquals("The provided login is already in use.", exception.getMessage());
        verify(userGateway).existsByLogin("existingUser");
    }
}
