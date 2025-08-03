package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype.JdbcUserTypeEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTypeMapper implements IUserTypeMapper {

    /**
     * <h3>METODO - tojdbcUserTypeEntity</h3>
     * <p>
     * - Recebe como parametro entidade de modelo;
     * - Instancia a entidade jdbc;
     * - Populada a entidade jdbc, com a entidade de modelo
     * - retorna a entidade jdbc populada
     *
     * @param userType -> recebe como parametro entidade de modelo;
     * @return -> entidade jdbc
     */
    @Override
    public JdbcUserTypeEntity tojdbcUserTypeEntity(final UserType userType) {

        // Instanciando a entidade jdbc
        JdbcUserTypeEntity jdbcUserTypeEntity = new JdbcUserTypeEntity();

        // Populando a entidade jdbc, com as informações da entidade de modelo
        // Realizando assim uma conversão de entidade de modelo para entidade jdbc
        jdbcUserTypeEntity.setId(userType.getId());
        jdbcUserTypeEntity.setName(userType.getName());
        jdbcUserTypeEntity.setDescription(userType.getDescription());
        jdbcUserTypeEntity.setActive(userType.getActive());
        jdbcUserTypeEntity.setCreated(userType.getCreated());
        jdbcUserTypeEntity.setUpdate(userType.getUpdated());

        // Retorna a entidade jdbc populada
        return jdbcUserTypeEntity;
    }

    /**
     * <h3>METODO - toUserType</h3>
     * <p>
     * - Recebe como parametro entidade jdbc;
     * - Instancia a entidade modelo;
     * - Populada a entidade modelo, com a entidade jdbc
     * - retorna a entidade modelo
     *
     * @param jdbcUserTypeEntity -> recebe como parametro entidade jdbc;
     * @return -> entidade de modelo;
     */
    @Override
    public UserType toUserType(final JdbcUserTypeEntity jdbcUserTypeEntity) {

        // instanciando a entidade de modelo
        UserType userType = new UserType();

        // Populando a entidade de Modelo
        // Realizando assim uma conversão de entidade jdbc para entidade de modelo
        userType.setId(jdbcUserTypeEntity.getId());
        userType.setName(jdbcUserTypeEntity.getName());
        userType.setDescription(jdbcUserTypeEntity.getDescription());
        userType.setActive(jdbcUserTypeEntity.getActive());
        userType.setCreated(jdbcUserTypeEntity.getCreated());
        userType.setUpdated(jdbcUserTypeEntity.getUpdate());

        // Retornando a entidade de modelo populada
        return userType;
    }

    /**
     * <h3>METODO - toUserType</h3>
     * <p>
     * - Recebe um DTO como parametro;
     * - Instancia a entidade modelo;
     * - Populada a entidade modelo, com o DTO request
     * - retorna a entidade modelo
     *
     * @param userTypeRequestDTO -> recebe um DTO
     * @return -> entidade de modelo;
     */
    @Override
    public UserType toUserType(final UserTypeRequestDTO userTypeRequestDTO) {

        // intancia da entidade de modelo
        UserType userType = new UserType();

        // Populando a entidade modelo, com o DTO de request
        userType.setName(userTypeRequestDTO.name());
        userType.setDescription(userTypeRequestDTO.description());

        // Retornando a entidade de modelo populada
        return userType;
    }

    /**
     * <h3>METODO - toUserTypeResponseDto</h3>
     * <p>
     * - Recebe uma entidade de modelo;
     * - Instancia o DTO Response;
     * - Popula com a entidade modelo;
     * - retorna o DTO response;
     *
     * @param userType -> recebe entidade de modelo
     * @return -> DTO response
     */
    @Override
    public UserTypeResponseDTO toUserTypeResponseDto(UserType userType) {
        return new UserTypeResponseDTO(
                userType.getId(),
                userType.getName(),
                userType.getDescription(),
                userType.getActive());
    }

    @Override
    public List<UserTypeResponseDTO> toListUserTypeResponseDto(List<UserType> userTypeList) {
        return userTypeList.stream().map(this::toUserTypeResponseDto).toList();
    }

    @Override
    public UserType toUserType(UserTypeRequestDTO userTypeRequestDTO, Long id) {
        UserType userType = new UserType();
        userType.setName(userTypeRequestDTO.name());
        userType.setDescription(userTypeRequestDTO.description());
        userType.setId(id);
        return userType;
    }
}
