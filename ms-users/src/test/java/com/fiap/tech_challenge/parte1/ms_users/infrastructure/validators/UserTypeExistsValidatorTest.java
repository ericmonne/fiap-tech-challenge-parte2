package com.fiap.tech_challenge.parte1.ms_users.infrastructure.validators;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserTypeDoesNotExistException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTypeExistsValidatorTest {

    private UserTypeGateway userTypeGateway;
    private UserTypeExistsValidator validator;

    @BeforeEach
    void setUp() {
        userTypeGateway = mock(UserTypeGateway.class);
        validator = new UserTypeExistsValidator(userTypeGateway);
    }

    @Test
    void shouldPassWhenUserTypeExists() {
        UserType userType = new UserType();
        userType.setName("ADMIN");

        User user = new User();
        user.setUserType(userType);

        when(userTypeGateway.existsByName("ADMIN")).thenReturn(true);

        assertDoesNotThrow(() -> validator.validate(user));

        verify(userTypeGateway).existsByName("ADMIN");
    }

    @Test
    void shouldThrowWhenUserTypeIsNull() {
        User user = new User();
        user.setUserType(null);

        UserTypeDoesNotExistException ex = assertThrows(UserTypeDoesNotExistException.class, () -> validator.validate(user));

        assertEquals("The provided user type does not exist. Please try again with a valid user type name.", ex.getMessage());
        verify(userTypeGateway, never()).existsByName(any());
    }

    @Test
    void shouldThrowWhenUserTypeDoesNotExist() {
        UserType userType = new UserType();
        userType.setName("NON_EXISTENT");

        User user = new User();
        user.setUserType(userType);

        when(userTypeGateway.existsByName("NON_EXISTENT")).thenReturn(false);

        UserTypeDoesNotExistException ex = assertThrows(UserTypeDoesNotExistException.class, () -> validator.validate(user));

        assertEquals("The provided user type does not exist. Please try again with a valid user type name.", ex.getMessage());
        verify(userTypeGateway).existsByName("NON_EXISTENT");
    }
}
