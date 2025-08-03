package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.ChangePasswordCommand;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.service.PasswordPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ChangePasswordUserUseCaseImplTest {

    private UserGateway userGateway;
    private PasswordEncoder passwordEncoder;
    private PasswordPolicy passwordPolicy;
    private ChangePasswordUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        passwordEncoder = mock(PasswordEncoder.class);
        passwordPolicy = mock(PasswordPolicy.class);
        useCase = new ChangePasswordUserUseCaseImpl(userGateway, passwordEncoder, passwordPolicy);
    }

    @Test
    void shouldChangePasswordSuccessfully() {
        // given
        UUID userId = UUID.randomUUID();
        String oldPassword = "oldPass";
        String newPassword = "newPass";
        String encodedNewPassword = "encodedNewPass";

        User user = mock(User.class);
        when(user.getPassword()).thenReturn("storedHashedPass");

        when(userGateway.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, "storedHashedPass")).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);

        ChangePasswordCommand command = new ChangePasswordCommand(oldPassword, newPassword);

        // when
        useCase.execute(userId, command);

        // then
        verify(passwordPolicy).validate(true, false);
        verify(userGateway).changePassword(userId, encodedNewPassword);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        // given
        UUID userId = UUID.randomUUID();
        ChangePasswordCommand command = new ChangePasswordCommand("old", "new");
        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        // when / then
        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> useCase.execute(userId, command));

        assertTrue(ex.getMessage().contains(userId.toString()));
        verify(userGateway, never()).changePassword(any(), any());
    }

    @Test
    void shouldThrowWhenPasswordPolicyFails() {
        // given
        UUID userId = UUID.randomUUID();
        String oldPassword = "oldPass";
        String newPassword = "oldPass"; // same as old to fail the policy

        User user = mock(User.class);
        when(user.getPassword()).thenReturn("storedPass");

        when(userGateway.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(oldPassword, "storedPass")).thenReturn(true);

        ChangePasswordCommand command = new ChangePasswordCommand(oldPassword, newPassword);

        doThrow(new IllegalArgumentException("Password policy violation"))
                .when(passwordPolicy).validate(true, true);

        // when / then
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(userId, command));

        verify(userGateway, never()).changePassword(any(), any());
    }
}
