package com.fiap.tech_challenge.parte1.ms_users.application.port.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype.JdbcUserTypeEntity;

import java.util.List;

public interface IUserTypeMapper {

    // ---------- Converte [ENTIDADE A ENTIDADE] ----------------------
    /**
     * <h3>METODO - tojdbcUserTypeEntity.</h3>
     *
     * - Recebe como parametro UserType entidade de Modelo;
     * - converte para entidade jdbc;
     * - Retorna uma entidade jdbc;
     *
     * @param userType -> Entidade de modelo
     * @return -> UserTypeResponseDTO
     * */
    JdbcUserTypeEntity tojdbcUserTypeEntity(final UserType userType); // 2°

    /**
     * <h3>METODO - toUserType.</h3>
     *
     * - Recebe como parametro uma entidade jdbc;
     * - converte para entidade modelo;
     * - Retorna uma entidade modelo;
     *
     * @param jdbcUserTypeEntity -> Entidade de modelo
     * @return -> UserTypeResponseDTO
     * */
    UserType toUserType(final JdbcUserTypeEntity jdbcUserTypeEntity); // 3°
    // ---------------------------------------------------


    // ---------- Converte [DTO A ENTIDADE]----------------------
    /**
     * <h3>METODO - toUserType.</h3>
     *
     * - Recebe como parametro um DTO;
     * - converte para entidade modelo;
     * - Retorna uma entidade modelo;
     *
     * @param userTypeRequestDTO -> Entidade de modelo
     * @return -> entidade de modelo
     * */
    UserType toUserType(final UserTypeRequestDTO userTypeRequestDTO); // 1°
    // ---------------------------------------------------


    // ---------- Converte [ENTIDADE A DTO]----------------------
    UserTypeResponseDTO toUserTypeResponseDto(final UserType userType);// 4°
    // ---------------------------------------------------

    List<UserTypeResponseDTO> toListUserTypeResponseDto(final List<UserType> userTypeList);
}
