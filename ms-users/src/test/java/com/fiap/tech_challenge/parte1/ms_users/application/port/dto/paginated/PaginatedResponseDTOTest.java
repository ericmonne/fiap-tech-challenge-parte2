package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginatedResponseDTOTest {

    @Test
    void shouldCreatePaginatedResponseWithCorrectValues() {
        List<String> content = List.of("item1", "item2", "item3");
        int totalItems = 3;
        int totalPages = 1;
        int currentPage = 0;
        int pageSize = 10;

        PaginatedResponseDTO<String> dto = new PaginatedResponseDTO<>(
                content, totalItems, totalPages, currentPage, pageSize
        );

        assertEquals(content, dto.content());
        assertEquals(totalItems, dto.totalItems());
        assertEquals(totalPages, dto.totalPages());
        assertEquals(currentPage, dto.currentPage());
        assertEquals(pageSize, dto.pageSize());
    }

    @Test
    void shouldSupportEmptyContentList() {
        PaginatedResponseDTO<Integer> dto = new PaginatedResponseDTO<>(
                List.of(), 0, 0, 0, 10
        );

        assertNotNull(dto.content());
        assertTrue(dto.content().isEmpty());
        assertEquals(0, dto.totalItems());
    }

    @Test
    void shouldWorkWithDifferentGenericTypes() {
        PaginatedResponseDTO<Integer> intDto = new PaginatedResponseDTO<>(
                List.of(1, 2, 3), 3, 1, 0, 3
        );
        assertEquals(3, intDto.content().size());

        PaginatedResponseDTO<Double> doubleDto = new PaginatedResponseDTO<>(
                List.of(1.1, 2.2), 2, 1, 0, 2
        );
        assertEquals(2, doubleDto.content().size());
    }
}
