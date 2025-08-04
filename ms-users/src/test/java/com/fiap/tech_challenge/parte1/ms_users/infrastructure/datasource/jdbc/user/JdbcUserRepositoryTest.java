package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JdbcUserRepositoryTest {

    @Mock
    private JdbcClient jdbcClient;

    @InjectMocks
    private JdbcUserRepository jdbcUserRepository;

    @Test
    void shouldCreateUser() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> jdbcUserRepository.save(new JdbcUserEntity()));
    }

    @Test
    void shouldFindUserByLogin() {
        JdbcClient.MappedQuerySpec<JdbcUserEntity> mappedQuerySpec = getAddressMappedQuerySpec();
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.query(any(RowMapper.class))).thenReturn(mappedQuerySpec);
        assertDoesNotThrow(() -> jdbcUserRepository.findByLogin("login"));
    }

    private JdbcClient.MappedQuerySpec<JdbcUserEntity> getAddressMappedQuerySpec() {
        return new JdbcClient.MappedQuerySpec<>() {
            @Override
            public Stream<JdbcUserEntity> stream() {
                return Stream.empty();
            }

            @Override
            public List<JdbcUserEntity> list() {
                return List.of(new JdbcUserEntity());
            }

            @Override
            public Optional<JdbcUserEntity> optional() {
                return Optional.of(new JdbcUserEntity());
            }
        };
    }

}