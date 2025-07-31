package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginatedResultTest {

    @Test
    void shouldCreatePaginatedResultWithCorrectValues() {
        List<String> content = List.of("Item1", "Item2");
        int totalItems = 2;
        int totalPages = 1;

        PaginatedResult<String> result = new PaginatedResult<>(content, totalItems, totalPages);

        assertEquals(content, result.content());
        assertEquals(totalItems, result.totalItems());
        assertEquals(totalPages, result.totalPages());
    }

    @Test
    void shouldThrowExceptionWhenContentIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () ->
                new PaginatedResult<>(null, 0, 0)
        );
        assertEquals("Content cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTotalItemsIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new PaginatedResult<>(List.of("Item"), -1, 0)
        );
        assertEquals("Total items cannot be negative", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTotalPagesIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new PaginatedResult<>(List.of("Item"), 1, -1)
        );
        assertEquals("Total pages cannot be negative", exception.getMessage());
    }

    @Test
    void shouldSupportEmptyContentList() {
        PaginatedResult<String> result = new PaginatedResult<>(List.of(), 0, 0);

        assertNotNull(result.content());
        assertTrue(result.content().isEmpty());
    }

    @Test
    void shouldSupportDifferentGenericTypes() {
        PaginatedResult<Integer> intResult = new PaginatedResult<>(List.of(1, 2, 3), 3, 1);
        assertEquals(3, intResult.content().size());

        PaginatedResult<Double> doubleResult = new PaginatedResult<>(List.of(1.1, 2.2), 2, 1);
        assertEquals(2, doubleResult.content().size());

        PaginatedResult<Object> objectResult = new PaginatedResult<>(List.of(new Object()), 1, 1);
        assertEquals(1, objectResult.totalItems());
    }
}

