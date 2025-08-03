package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTypeGatewayImplTest {

    @Mock
    private UserTypeDataSource userTypeDataSource;

    @InjectMocks
    private UserTypeGatewayImpl userTypeGateway;

    private UserType userType;
    private final Long userId = 1L;
    private final String userName = "ADMIN";

    @BeforeEach
    void setUp() {
        userType = new UserType("ADMIN", "Administrator", true);
    }

    @Test
    void createUserType_ShouldDelegateToDataSource() {
        when(userTypeDataSource.createUserType(userType)).thenReturn(userId);

        Long result = userTypeGateway.createUserType(userType);

        assertEquals(userId, result);
        verify(userTypeDataSource).createUserType(userType);
    }

    @Test
    void deactivate_ShouldDelegateToDataSource() {
        doNothing().when(userTypeDataSource).deactivate(userId);

        userTypeGateway.deactivate(userId);

        verify(userTypeDataSource).deactivate(userId);
    }

    @Test
    void reactivate_ShouldDelegateToDataSource() {
        doNothing().when(userTypeDataSource).reactivate(userId);

        userTypeGateway.reactivate(userId);

        verify(userTypeDataSource).reactivate(userId);
    }

    @Test
    void update_ShouldDelegateToDataSource() {
        doNothing().when(userTypeDataSource).update(userType);

        userTypeGateway.update(userType);

        verify(userTypeDataSource).update(userType);
    }

    @Test
    void findAll_ShouldDelegateToDataSource() {
        List<UserType> expectedList = List.of(userType);
        when(userTypeDataSource.findAll(10, 0)).thenReturn(expectedList);

        List<UserType> result = userTypeGateway.findAll(10, 0);

        assertEquals(expectedList, result);
        verify(userTypeDataSource).findAll(10, 0);
    }

    @Test
    void findById_ShouldReturnPresent_WhenExists() {
        when(userTypeDataSource.findById(userId)).thenReturn(Optional.of(userType));

        Optional<UserType> result = userTypeGateway.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(userType, result.get());
        verify(userTypeDataSource).findById(userId);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        when(userTypeDataSource.findById(userId)).thenReturn(Optional.empty());

        Optional<UserType> result = userTypeGateway.findById(userId);

        assertTrue(result.isEmpty());
        verify(userTypeDataSource).findById(userId);
    }

    @Test
    void existsByName_ShouldReturnTrue_WhenExists() {
        when(userTypeDataSource.existsByName(userName)).thenReturn(true);

        boolean result = userTypeGateway.existsByName(userName);

        assertTrue(result);
        verify(userTypeDataSource).existsByName(userName);
    }

    @Test
    void existsByName_ShouldReturnFalse_WhenNotExists() {
        when(userTypeDataSource.existsByName(userName)).thenReturn(false);

        boolean result = userTypeGateway.existsByName(userName);

        assertFalse(result);
        verify(userTypeDataSource).existsByName(userName);
    }

    @Test
    void findByName_ShouldReturnPresent_WhenExists() {
        when(userTypeDataSource.findByName(userName)).thenReturn(Optional.of(userType));

        Optional<UserType> result = userTypeGateway.findByName(userName);

        assertTrue(result.isPresent());
        assertEquals(userType, result.get());
        verify(userTypeDataSource).findByName(userName);
    }

    @Test
    void findByName_ShouldReturnEmpty_WhenNotExists() {
        when(userTypeDataSource.findByName(userName)).thenReturn(Optional.empty());

        Optional<UserType> result = userTypeGateway.findByName(userName);

        assertTrue(result.isEmpty());
        verify(userTypeDataSource).findByName(userName);
    }

    @Test
    void findAll_ShouldPassCorrectPaginationParameters() {
        List<UserType> expectedList = List.of(userType);
        when(userTypeDataSource.findAll(5, 2)).thenReturn(expectedList);

        List<UserType> result = userTypeGateway.findAll(5, 2);

        assertEquals(expectedList, result);
        verify(userTypeDataSource).findAll(5, 2);
    }
}