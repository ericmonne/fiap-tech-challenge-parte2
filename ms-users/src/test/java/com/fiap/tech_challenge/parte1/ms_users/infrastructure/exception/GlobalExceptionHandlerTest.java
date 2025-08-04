package com.fiap.tech_challenge.parte1.ms_users.infrastructure.exception;

import com.fiap.tech_challenge.parte1.ms_users.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/test");
        when(request.getMethod()).thenReturn("GET");
    }

    @Test
    void handleUserNotFoundException_shouldReturn404() {
        UserNotFoundException ex = new UserNotFoundException("User not found");

        ResponseEntity<Map<String, Object>> response = handler.handleUserNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals("User not found", response.getBody().get("message"));
    }

    @Test
    void handleTypeMismatch_shouldReturn400() {
        ResponseEntity<Object> response = handler.handleTypeMismatch(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("Parâmetro inválido"));
    }

    @Test
    void handleEmailAlreadyExists_shouldReturn409() {
        EmailAlreadyExistsException ex = new EmailAlreadyExistsException("Email already exists");

        ResponseEntity<Object> response = handler.handleEmailAlreadyExists(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already exists", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void handleLoginAlreadyExists_shouldReturn409() {
        LoginAlreadyExistsException ex = new LoginAlreadyExistsException("Login already exists");

        ResponseEntity<Object> response = handler.handleLoginAlreadyExists(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Login already exists", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void handleDuplicatedAddress_shouldReturn409() {
        DuplicatedAddressException ex = new DuplicatedAddressException("Address duplicated");

        ResponseEntity<Object> response = handler.handleDuplicatedAddress(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Address duplicated", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void handleMissingParams_shouldReturn400() {
        MissingServletRequestParameterException ex =
                new MissingServletRequestParameterException("param", "String");

        ResponseEntity<Object> response = handler.handleMissingParams(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertTrue(((Map<?, ?>) response.getBody()).get("message").toString().contains("param"));
    }

    @Test
    void handleValidationExceptions_shouldReturn400WithFieldErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "name", "must not be blank");
        when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Object> response = handler.handleValidationExceptions(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");

        assertEquals("must not be blank", fieldErrors.get("name"));
    }

    @Test
    void handleInvalidPasswordException_shouldReturn400() {
        InvalidPasswordException ex = new InvalidPasswordException("Weak password");

        ResponseEntity<Object> response = handler.handleInvalidPasswordException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals("Weak password", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void handleGeneralException_shouldReturn500() {
        Exception ex = new Exception("Something went wrong");

        ResponseEntity<Object> response = handler.handleEx(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals("Something went wrong", ((Map<?, ?>) response.getBody()).get("message"));
    }
}
