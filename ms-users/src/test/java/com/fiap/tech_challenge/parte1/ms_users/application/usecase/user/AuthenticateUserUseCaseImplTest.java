package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.AuthenticatedUser;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.AuthenticationDataDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.AuthenticationRequest;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.TokenJWTInfoDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.token.TokenProvider;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.Authenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AuthenticateUserUseCaseImplTest {

    private TokenProvider tokenProvider;
    private Authenticator authenticator;
    private AuthenticateUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        tokenProvider = mock(TokenProvider.class);
        authenticator = mock(Authenticator.class);
        useCase = new AuthenticateUserUseCaseImpl(tokenProvider, authenticator);
    }

    @Test
    void shouldAuthenticateUserAndReturnToken() {
        // given
        AuthenticationDataDTO credentials = new AuthenticationDataDTO("user@example.com", "password123");
        AuthenticatedUser authenticatedUser = new AuthenticatedUser("user@example.com", "OWNER");
        String expectedToken = "mocked.jwt.token";

        when(authenticator.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(authenticatedUser);
        when(tokenProvider.generateToken("user@example.com"))
                .thenReturn(expectedToken);

        // when
        TokenJWTInfoDTO result = useCase.execute(credentials);

        // then
        assertNotNull(result);
        assertEquals(expectedToken, result.tokenJWT());

        // verifying interaction
        ArgumentCaptor<AuthenticationRequest> captor = ArgumentCaptor.forClass(AuthenticationRequest.class);
        verify(authenticator).authenticate(captor.capture());
        AuthenticationRequest authRequest = captor.getValue();
        assertEquals("user@example.com", authRequest.login());
        assertEquals("password123", authRequest.password());

        verify(tokenProvider).generateToken("user@example.com");
    }
}
