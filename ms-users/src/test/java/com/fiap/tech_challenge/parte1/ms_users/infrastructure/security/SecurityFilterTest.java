package com.fiap.tech_challenge.parte1.ms_users.infrastructure.security;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.token.TokenProvider;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityFilterTest {

    @Mock
    TokenProvider tokenProvider;
    @Mock
    UserGateway userGateway;
    @Mock
    UserTypeGateway userTypeGateway;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;

    @InjectMocks
    SecurityFilter filter;

    private final String VALID_TOKEN = "valid-token";
    private final String USER_LOGIN = "john";

    @BeforeEach
    void setup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateSuccessfully_whenTokenValidAndUserExistsAndTypeExists() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_TOKEN);
        when(tokenProvider.extractUserLoginFromToken(VALID_TOKEN)).thenReturn(USER_LOGIN);

        User user = new User();
        user.setLogin(USER_LOGIN);
        UserType ut = new UserType();
        Long USERTYPE_ID = 99L;
        ut.setId(USERTYPE_ID);
        user.setUserType(ut);

        when(userGateway.findByLogin(USER_LOGIN)).thenReturn(Optional.of(user));

        UserType loadedType = new UserType();
        loadedType.setId(USERTYPE_ID);
        loadedType.setName("ADMIN");

        when(userTypeGateway.findById(USERTYPE_ID)).thenReturn(Optional.of(loadedType));

        filter.doFilterInternal(request, response, filterChain);

        var auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth);
        assertInstanceOf(UsernamePasswordAuthenticationToken.class, auth);
        assertInstanceOf(UserDetailsAdapter.class, auth.getPrincipal());
        UserDetailsAdapter adapter = (UserDetailsAdapter) auth.getPrincipal();
        assertEquals("john", adapter.getUsername());

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldContinueFilter_whenNoAuthorizationHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_TOKEN);
        when(tokenProvider.extractUserLoginFromToken(VALID_TOKEN)).thenReturn(USER_LOGIN);
        when(userGateway.findByLogin(USER_LOGIN)).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class, () ->
                filter.doFilterInternal(request, response, filterChain));
        assertTrue(ex.getMessage().contains(USER_LOGIN));
    }

    @Test
    void shouldThrowWhenUserTypeNotFound() {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_TOKEN);
        when(tokenProvider.extractUserLoginFromToken(VALID_TOKEN)).thenReturn(USER_LOGIN);

        User user = new User();
        user.setLogin(USER_LOGIN);
        user.setUserType(new UserType());
        when(userGateway.findByLogin(USER_LOGIN)).thenReturn(Optional.of(user));

        when(userTypeGateway.findById(null)).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class, () ->
                filter.doFilterInternal(request, response, filterChain));
        assertTrue(ex.getMessage().contains("not found"));
    }
}
