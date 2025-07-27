package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated;

import java.util.List;

public record PaginatedResponseDTO<T>(List<T> content,
                                      int totalItems,
                                      int totalPages,
                                      int currentPage,
                                      int pageSize) {
}
