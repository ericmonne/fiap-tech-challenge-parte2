package com.fiap.tech_challenge.parte1.ms_users.infrastructure.openapi;

import com.fiap.tech_challenge.parte1.ms_users.api.routes.UserRoutes;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(UserRoutes.USERS_BASE)
public interface UsersApi {

    @GetMapping(UserRoutes.ID)
    @Operation(
            summary = "Busca",
            description = "Retorna os dados de um usuário, pesquisado por ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UsersResponseDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    default ResponseEntity<UsersResponseDTO> getById(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping
    @Operation(
            summary = "Lista usuários",
            description = "Retorna uma lista paginada de usuários",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de usuários",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UsersResponseDTO.class))
                            )
                    )
            }
    )
    default ResponseEntity<List<UsersResponseDTO>> findAllUsers(
            @RequestParam int size,
            @RequestParam int page) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @PostMapping
    @Operation(
            summary = "Cadastro",
            description = "Registra um novo usuário e retorna dados cadastrais com um token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateUserDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                            {
                                              "timestamp": "2025-05-31T15:00:00Z",
                                              "status": 400,
                                              "errors": [
                                                "User field 'email' must be a valid email address",
                                                "User must have at least one Address"
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(hidden = true)))
    })
    default ResponseEntity<CreateUserDTO> create(@RequestBody @Valid UsersRequestDTO dto) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @PostMapping(UserRoutes.LOGIN)
    @Operation(
            summary = "Login",
            description = "Realiza a autenticação de um usuário no sistema e retorna um token JWT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenJWTInfoDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                            {
                                              "timestamp": "2025-05-31T15:00:00Z",
                                              "status": 400,
                                              "errors": [
                                                "Field 'login' must not be blank",
                                                "Field 'password' must not be blank"
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Authentication failed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                            {
                                              "timestamp": "2025-05-31T15:00:00Z",
                                              "status": 401,
                                              "error": "Unauthorized",
                                              "message": "Invalid login or password"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(hidden = true)))
    })
    default ResponseEntity<TokenJWTInfoDTO> executeLogin(@RequestBody @Valid AuthenticationDataDTO data) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @PatchMapping(UserRoutes.ID)
    @Operation(
            summary = "Ativa/Inativa usuário",
            description = "Ativa ou inativa um usuário com base no valor da query param activate.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User activated / User deactivated",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string"))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid UUID or parameter", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(hidden = true)))
    })
    default ResponseEntity<String> toggleActivation(
            @PathVariable UUID id,
            @RequestParam boolean activate) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @PatchMapping(UserRoutes.ID_AND_PASSWORD)
    @Operation(
            summary = "Altera senha",
            description = "Realiza a alteração da senha de um usuário.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully!",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(type = "string", example = "Password updated!"))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                            {
                                              "timestamp": "2025-05-31T15:00:00Z",
                                              "status": 400,
                                              "errors": [
                                                "Field 'oldPassword' is required",
                                                "Field 'newPassword' is required"
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(hidden = true)))
    })
    default ResponseEntity<String> changePassword(
            @PathVariable UUID id,
            @RequestBody @Valid ChangePasswordRequestDTO dto) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @PutMapping(UserRoutes.ID)
    @Operation(
            summary = "Atualiza",
            description = "Atualiza os dados cadastrais de um usuario, pesquisado por ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsersResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                            {
                                              "timestamp": "2025-05-31T15:00:00Z",
                                              "status": 400,
                                              "errors": [
                                                "User field 'email' must be a valid email address",
                                                "User must have at least one address"
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(hidden = true)))
    })
    default ResponseEntity<UsersResponseDTO> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateUserDTO dto) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
