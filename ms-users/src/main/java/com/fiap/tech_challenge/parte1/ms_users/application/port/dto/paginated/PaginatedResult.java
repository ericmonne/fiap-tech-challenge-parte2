package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated;

import java.util.List;
import java.util.Objects;

public record PaginatedResult<T>(
        List<T> content,
        int totalItems,
        int totalPages
) {
    public PaginatedResult {
        Objects.requireNonNull(content, "Content cannot be null");

        if (totalItems < 0) {
            throw new IllegalArgumentException("Total items cannot be negative");
        }
        if (totalPages < 0) {
            throw new IllegalArgumentException("Total pages cannot be negative");
        }
    }
}
