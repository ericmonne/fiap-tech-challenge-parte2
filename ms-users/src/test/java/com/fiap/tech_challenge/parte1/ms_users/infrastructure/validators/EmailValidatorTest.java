package com.fiap.tech_challenge.parte1.ms_users.infrastructure.validators;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.EmailAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailValidatorTest {

    private UserGateway userGateway;
    private EmailValidator validator;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        validator = new EmailValidator(userGateway);
    }

    @Test
    void shouldPassWhenEmailDoesNotExist() {
        User user = new User();
        user.setEmail("newuser@example.com");

        when(userGateway.existsByEmail("newuser@example.com")).thenReturn(false);

        assertDoesNotThrow(() -> validator.validate(user));
        verify(userGateway).existsByEmail("newuser@example.com");
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        User user = new User();
        user.setEmail("existinguser@example.com");

        when(userGateway.existsByEmail("existinguser@example.com")).thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> validator.validate(user));

        assertEquals("The provided email is already in use.", exception.getMessage());
        verify(userGateway).existsByEmail("existinguser@example.com");
    }
}
