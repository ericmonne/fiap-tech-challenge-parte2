package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

import java.util.List;
import java.util.Optional;

public class JdbcUserTypeDataSource implements UserTypeDataSource {

    private final JdbcUserTypeRepository jdbcUserTypeRepository;
    private final com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper iUserTypeMapper;

    public JdbcUserTypeDataSource(
            final JdbcUserTypeRepository jdbcUserTypeRepository,
            final com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.usertype.IUserTypeMapper iUserTypeMapper
    ) {
        this.jdbcUserTypeRepository = jdbcUserTypeRepository;
        this.iUserTypeMapper = iUserTypeMapper;
    }

    /**
     * <h3>METODO - createUserType</h3>
     *
     * - Recebe como parametro entidade de modelo;
     * - converte atraves do Mepper para uma entidade jdbc, armazenado em uma variavel;
     * - salva a variavel do tipo entidade jdbc na base de dados
     *
     * @param userType -> Recebe como parametro entidade de modelo
     * */
    @Override
    public void createUserType(final UserType userType) {
        var convertToEntity = this.iUserTypeMapper.tojdbcUserTypeEntity(userType);
        this.jdbcUserTypeRepository.save(convertToEntity);
    }

    /**
     * <h3>METODO - deactivate</h3>
     *
     * - Recebe como parametro o ID;
     * - Desativa(false) o usuario do respectivo ID passado como parametro;
     *
     * @param id -> Recebe como parametro o ID
     * */
    @Override
    public void deactivate(final Long id) {
        this.jdbcUserTypeRepository.deactivate(id);
    }

    /**
     * <h3>METODO - reactivate</h3>
     *
     * - Recebe como parametro o ID;
     * - Ativa(true) o usuario do respectivo ID passado como parametro;
     *
     * @param id -> Recebe como parametro o ID
     * */
    @Override
    public void reactivate(final Long id) {
        this.jdbcUserTypeRepository.reactivate(id);
    }

    /**
     * <h3>METODO - update</h3>
     *
     * - Recebe como parametro uma entidade de modelo;
     * - converte a entidade de modelo, em entidade jdbc;
     * - atualiza na base de dados;
     *
     * @param userType -> Recebe como parametro entidade de modelo
     * */
    @Override
    public void update(final UserType userType) {
        var convertToEntity = this.iUserTypeMapper.tojdbcUserTypeEntity(userType);
        this.jdbcUserTypeRepository.update(convertToEntity);
    }

    /**
     * <h3>METODO - findAll</h3>
     *
     * - Recebe como parametro uma entidade de modelo;
     * - converte a entidade de modelo, em entidade Entity;
     * - atualiza na base de dados;
     *
     * @param size -> parâmetro especifica o tamanho da página, ou seja,
     *             o número máximo de itens que serão retornados em cada requisição.
     * @param offset -> indica quantos itens devem ser ignorados antes de começar a retornar os resultados.
     * @return -> retorna uma lista de Entidades de Modelo.
     * */
    @Override
    public List<UserType> findAll(final int size, final int offset) {
        return this.jdbcUserTypeRepository.findAll(size,offset);
    }

    /**
     * <h3>METODO - findById</h3>
     *
     * - Recebe como parametro o ID;
     * - Busca esse ID na base de dados;
     * - Retorna uma lista que pode conter um objeto do tipo entidade de modelo;
     * - Pode retornar uma lista nula, para evitar uma Ecxeption;
     *
     * @param id -> recebe o ID
     * @return -> retorna uma lista de entidade modelo ou retorna uma lista nula
     * */
    @Override
    public Optional<UserType> findById(final Long id) {
        return this.jdbcUserTypeRepository.findById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.jdbcUserTypeRepository.existsByName(name);
    }
}
