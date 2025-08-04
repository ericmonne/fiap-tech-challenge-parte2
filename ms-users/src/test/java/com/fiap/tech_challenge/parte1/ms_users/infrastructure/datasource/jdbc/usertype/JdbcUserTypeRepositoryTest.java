package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JdbcUserTypeRepositoryTest {

    @Mock
    private JdbcClient jdbcClient;

    @InjectMocks
    private JdbcUserTypeRepository jdbcUserTypeRepository;

    @Test
    void testSave() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update(any(KeyHolder.class))).thenReturn(1);
        assertDoesNotThrow(() -> jdbcUserTypeRepository.save(new JdbcUserTypeEntity()));
    }

    @Test
    void testUpdate() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> jdbcUserTypeRepository.update(new JdbcUserTypeEntity()));
    }

    @Test
    void testDeactivate() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> jdbcUserTypeRepository.deactivate(1L));
    }

    @Test
    void testReactivate() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> jdbcUserTypeRepository.reactivate(1L));
    }

    @Test
    void testFindAll() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        JdbcClient.MappedQuerySpec<UserType> mappedQuerySpec = mock(JdbcClient.MappedQuerySpec.class);

        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.params(anyList())).thenReturn(statementSpec);
        when(statementSpec.query(eq(UserType.class))).thenReturn(mappedQuerySpec);
        when(mappedQuerySpec.list()).thenReturn(List.of(new UserType()));

        assertDoesNotThrow(() -> jdbcUserTypeRepository.findAll(10, 0));
    }


    private JdbcClient.MappedQuerySpec<UserType> getUserTypeMappedQuerySpec() {
        return new JdbcClient.MappedQuerySpec<>() {
            @Override
            public Stream<UserType> stream() {
                return Stream.empty();
            }

            @Override
            public List<UserType> list() {
                return List.of(new UserType());
            }

            @Override
            public Optional<UserType> optional() {
                return Optional.of(new UserType());
            }
        };
    }

    @Test
    void testFindById() {
        JdbcClient.MappedQuerySpec<UserType> mappedQuerySpec = getUserTypeMappedQuerySpec();
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.query(UserType.class)).thenReturn(mappedQuerySpec);
        assertDoesNotThrow(() -> jdbcUserTypeRepository.findById(1L));
    }


}