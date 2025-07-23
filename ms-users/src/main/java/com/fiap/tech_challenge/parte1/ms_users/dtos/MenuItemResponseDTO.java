package com.fiap.tech_challenge.parte1.ms_users.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemResponseDTO(UUID id,
                                  String name,
                                  String description,
                                  BigDecimal price,
                                  Boolean availableOnlyOnSite,
                                  String imagePath
) {
}
