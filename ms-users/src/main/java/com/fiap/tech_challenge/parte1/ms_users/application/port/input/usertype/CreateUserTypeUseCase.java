package com.fiap.tech_challenge.parte1.ms_users.application.port.input.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;

public interface CreateUserTypeUseCase {
    /**
     * <h3>METODO - execute</h3>
     *
     * - Recebe como parametro um RequestDto;
     * - Não retorna nada;
     *
     * @param userTypeRequestDTO -> DTO
     * */
    void execute(final UserTypeRequestDTO userTypeRequestDTO);
}
