package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.address;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AddressGatewayImplTest {

    private AddressDataSource addressDataSource;
    private AddressGatewayImpl addressGateway;

    @BeforeEach
    void setUp() {
        addressDataSource = mock(AddressDataSource.class);
        addressGateway = new AddressGatewayImpl(addressDataSource);
    }

    @Test
    void shouldSaveUserAddress() {
        List<Address> addresses = List.of(new Address());
        UUID userId = UUID.randomUUID();

        addressGateway.saveUserAddress(addresses, userId);

        verify(addressDataSource).saveUserAddress(addresses, userId);
    }

    @Test
    void shouldUpdateUserAddress() {
        List<Address> addresses = List.of(new Address());
        UUID userId = UUID.randomUUID();

        addressGateway.updateUserAddress(addresses, userId);

        verify(addressDataSource).updateUserAddress(addresses, userId);
    }

    @Test
    void shouldFindAllByUserIds() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Set<UUID> ids = Set.of(id1, id2);
        List<Address> expected = List.of(new Address());

        when(addressDataSource.findAllByUserIds(ids)).thenReturn(expected);

        List<Address> result = addressGateway.findAllByUserIds(ids);

        assertEquals(expected, result);
        verify(addressDataSource).findAllByUserIds(ids);
    }

    @Test
    void shouldFindAllByUserId() {
        UUID id = UUID.randomUUID();
        Set<UUID> idSet = Set.of(id);
        List<Address> expected = List.of(new Address());

        when(addressDataSource.findAllByUserIds(idSet)).thenReturn(expected);

        List<Address> result = addressGateway.findAllByUserId(id);

        assertEquals(expected, result);
        verify(addressDataSource).findAllByUserIds(idSet);
    }

    @Test
    void shouldFindByRestaurantId() {
        UUID restaurantId = UUID.randomUUID();
        Address address = new Address();

        when(addressDataSource.findByRestaurantId(restaurantId)).thenReturn(Optional.of(address));

        Optional<Address> result = addressGateway.findByRestaurantId(restaurantId);

        assertTrue(result.isPresent());
        assertEquals(address, result.get());
        verify(addressDataSource).findByRestaurantId(restaurantId);
    }

    @Test
    void shouldSaveRestaurantAddress() {
        UUID restaurantId = UUID.randomUUID();
        Address address = new Address();

        UUID result = addressGateway.saveRestaurantAddress(address, restaurantId);

        assertEquals(restaurantId, result);
        verify(addressDataSource).saveRestaurantAddress(address, restaurantId);
    }

    @Test
    void shouldUpdateRestaurantAddress() {
        UUID restaurantId = UUID.randomUUID();
        Address address = new Address();

        addressGateway.updateRestaurantAddress(address, restaurantId);

        verify(addressDataSource).updateRestaurantAddress(address, restaurantId);
    }
}
