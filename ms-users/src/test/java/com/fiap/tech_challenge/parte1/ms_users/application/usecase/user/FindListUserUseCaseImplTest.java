package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindListUserUseCaseImplTest {

    private UserGateway userGateway;
    private AddressGateway addressGateway;
    private FindListUserUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        addressGateway = mock(AddressGateway.class);
        useCase = new FindListUserUseCaseImpl(userGateway, addressGateway);
    }

    @Test
    void shouldReturnUsersWithAddresses() {
        // given
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        Address address = new Address();
        address.setUserId(userId);

        when(userGateway.findAll(10, 0)).thenReturn(List.of(user));
        when(addressGateway.findAllByUserIds(Set.of(userId))).thenReturn(List.of(address));

        // when
        List<User> result = useCase.execute(10, 1);

        // then
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getAddress().size());
        assertEquals(address, result.get(0).getAddress().get(0));
    }

    @Test
    void shouldReturnUsersWithEmptyAddressListWhenNoAddressesFound() {
        // given
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userGateway.findAll(5, 0)).thenReturn(List.of(user));
        when(addressGateway.findAllByUserIds(Set.of(userId))).thenReturn(Collections.emptyList());

        // when
        List<User> result = useCase.execute(5, 1);

        // then
        assertEquals(1, result.size());
        assertTrue(result.get(0).getAddress().isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersFound() {
        // given
        when(userGateway.findAll(5, 0)).thenReturn(Collections.emptyList());

        // when
        List<User> result = useCase.execute(5, 1);

        // then
        assertTrue(result.isEmpty());
        verify(addressGateway, never()).findAllByUserIds(any());
    }
}
