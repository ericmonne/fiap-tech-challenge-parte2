package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeactivateUserUseCaseImplTest {

    private UserGateway userGateway;
    private DeactivateUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        useCase = new DeactivateUserUseCaseImpl(userGateway);
    }

    @Test
    void shouldDeactivateUserWhenUserExists() {
        // given
        UUID userId = UUID.randomUUID();
        User user = mock(User.class);
        when(user.getId()).thenReturn(userId);
        when(userGateway.findById(userId)).thenReturn(Optional.of(user));

        // when
        useCase.execute(userId);

        // then
        verify(userGateway).deactivate(userId);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        // given
        UUID userId = UUID.randomUUID();
        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        // when / then
        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> useCase.execute(userId));

        assertTrue(ex.getMessage().contains(userId.toString()));
        verify(userGateway, never()).deactivate(any());
    }
}
