package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JdbcMenuItemRepositoryTest {

    @Mock
    private JdbcClient jdbcClient;

    @InjectMocks
    private JdbcMenuItemRepository jdbcMenuItemRepository;

    @Test
    void shouldFindByRestaurantId() {
        JdbcClient.MappedQuerySpec<MenuItem> mappedQuerySpec = getMenuItemMappedQuerySpec();
        JdbcClient.MappedQuerySpec<Integer> integerMappedQuerySpec = getIntegerMappedQuerySpec();
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.query(MenuItem.class)).thenReturn(mappedQuerySpec);
        when(statementSpec.query(Integer.class)).thenReturn(integerMappedQuerySpec);
        assertDoesNotThrow(() -> jdbcMenuItemRepository.findByRestaurantId(new MenuItemsByRestaurantRequestDTO(UUID.randomUUID(), 10, 20)));
    }

    @Test
    void shouldFindAllPaginated() {
        JdbcClient.MappedQuerySpec<MenuItem> mappedQuerySpec = getMenuItemMappedQuerySpec();
        JdbcClient.MappedQuerySpec<Integer> integerMappedQuerySpec = getIntegerMappedQuerySpec();
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.query(MenuItem.class)).thenReturn(mappedQuerySpec);
        when(statementSpec.query(Integer.class)).thenReturn(integerMappedQuerySpec);
        assertDoesNotThrow(() -> jdbcMenuItemRepository.findAllPaginated(10, 20));
    }

    @Test
    void shouldTestExistsById() {
        JdbcClient.MappedQuerySpec<Integer> integerMappedQuerySpec = getIntegerMappedQuerySpec();
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.query(Integer.class)).thenReturn(integerMappedQuerySpec);
        assertDoesNotThrow(() -> jdbcMenuItemRepository.existsById(UUID.randomUUID()));
    }

    @Test
    void shouldSaveMenuItem() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> jdbcMenuItemRepository.save(new JdbcMenuItemEntity()));
    }

    @Test
    void shouldUpdate() {
        JdbcClient.StatementSpec statementSpec = mock(JdbcClient.StatementSpec.class);
        when(jdbcClient.sql(anyString())).thenReturn(statementSpec);
        when(statementSpec.param(anyString(), any())).thenReturn(statementSpec);
        when(statementSpec.update()).thenReturn(1);
        assertDoesNotThrow(() -> jdbcMenuItemRepository.update(new JdbcMenuItemEntity()));
    }

    private JdbcClient.MappedQuerySpec<MenuItem> getMenuItemMappedQuerySpec() {
        return new JdbcClient.MappedQuerySpec<>() {
            @Override
            public Stream<MenuItem> stream() {
                return Stream.empty();
            }

            @Override
            public List<MenuItem> list() {
                return List.of(new MenuItem());
            }

            @Override
            public Optional<MenuItem> optional() {
                return Optional.of(new MenuItem());
            }
        };
    }

    private JdbcClient.MappedQuerySpec<Integer> getIntegerMappedQuerySpec() {
        return new JdbcClient.MappedQuerySpec<>() {
            @Override
            public Stream<Integer> stream() {
                return Stream.empty();
            }

            @Override
            public List<Integer> list() {
                return List.of(1);
            }

            @Override
            public Optional<Integer> optional() {
                return Optional.of(1);
            }

            @Override
            public Integer single() {
                return 1;
            }
        };
    }

}