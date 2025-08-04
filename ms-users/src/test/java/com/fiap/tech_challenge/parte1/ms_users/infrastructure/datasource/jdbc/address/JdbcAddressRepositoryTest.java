package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JdbcAddressRepositoryTest {

    @Mock
    private JdbcClient jdbcClient;

    @InjectMocks
    private JdbcAddressRepository jdbcAddressRepository;

    @Test
    void shouldSaveUserAddress() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> jdbcAddressRepository.saveUserAddress(List.of(new JdbcAddressEntity()), UUID.randomUUID()));
    }

    @Test
    void shouldSaveRestaurantAddress() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> jdbcAddressRepository.saveRestaurantAddress(new Address(), UUID.randomUUID()));
    }

    @Test
    void shouldUpdateRestaurantAddress() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> jdbcAddressRepository.updateRestaurantAddress(new Address(), UUID.randomUUID()));
    }

    @Test
    void shouldNotFindByRestaurantId() {
        Optional<Address> byRestaurantId = jdbcAddressRepository.findByRestaurantId(null);
        assertTrue(byRestaurantId.isEmpty());
    }

    @Test
    void shouldFindByRestaurantId() {
        JdbcClient.MappedQuerySpec<Address> mappedQuerySpec = getAddressMappedQuerySpec();
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.query(Address.class)).thenReturn(mappedQuerySpec);
        Optional<Address> byRestaurantId = jdbcAddressRepository.findByRestaurantId(UUID.randomUUID());
        assertTrue(byRestaurantId.isPresent());
    }

    @Test
    void shouldNotFindAllByUserIds() {
        List<Address> allByUserIds = jdbcAddressRepository.findAllByUserIds(null);
        assertTrue(allByUserIds.isEmpty());
        allByUserIds = jdbcAddressRepository.findAllByUserIds(new HashSet<>());
        assertTrue(allByUserIds.isEmpty());
    }

    @Test
    void shouldFindAllByUserIds() {
        JdbcClient.MappedQuerySpec<Address> mappedQuerySpec = getAddressMappedQuerySpec();
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.query(Address.class)).thenReturn(mappedQuerySpec);
        List<Address> allByUserIds = jdbcAddressRepository.findAllByUserIds(Set.of(UUID.randomUUID()));

        assertFalse(allByUserIds.isEmpty());
    }


    private JdbcClient.MappedQuerySpec<Address> getAddressMappedQuerySpec() {
        return new JdbcClient.MappedQuerySpec<>() {
            @Override
            public Stream<Address> stream() {
                return Stream.empty();
            }

            @Override
            public List<Address> list() {
                return List.of(new Address());
            }

            @Override
            public Optional<Address> optional() {
                return Optional.of(new Address());
            }
        };
    }


}