package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype;

import jakarta.validation.constraints.NotBlank;

public record UserTypeRequestDTO (
        @NotBlank(message = "The name field is required")
        String name,

        @NotBlank(message = "The description field is required" )
        String description,

        @NotBlank(message = "the active field is required [TRUE OR FALSE]")
        Boolean active
) {

}
