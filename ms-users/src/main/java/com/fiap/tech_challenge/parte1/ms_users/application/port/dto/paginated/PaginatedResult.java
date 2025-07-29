package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated;

import java.util.List;

public record PaginatedResult<T>(
        List<T> content,
        int totalItems,
        int totalPages
) {
}
