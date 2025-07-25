package com.fiap.tech_challenge.parte1.ms_users.infrastructure.security;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.token.TokenProvider;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Security filter that intercepts every HTTP request to authenticate users based on JWT tokens.
 * <p>
 * This filter extracts the JWT token from the Authorization header, validates it, retrieves the user details,
 * and sets the authentication in the SecurityContext if the token and user are valid.
 * </p>
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserGateway userGateway;

    public SecurityFilter(TokenProvider tokenProvider, UserGateway userGateway) {
        this.tokenProvider = tokenProvider;
        this.userGateway = userGateway;
    }


    /**
     * Filters incoming HTTP requests to authenticate users based on the JWT token provided in the Authorization header.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain to pass the request and response to the next filter
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenJWT = retrieveToken(request);
        if (tokenJWT != null) {
            String subject = tokenProvider.extractUserLoginFromToken(tokenJWT);
            UserDetails userDetails = userGateway.findByLogin(subject)
                    .map(UserDetailsAdapter::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Retrieves the JWT token from the Authorization header of the HTTP request.
     *
     * @param request the HTTP request
     * @return the JWT token string without the "Bearer " prefix, or null if the header is missing or malformed
     */
    private String retrieveToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
