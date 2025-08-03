package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcUserTypeRepository {

    // declarando o atributo para injeção de dependecia
    private final JdbcClient jdbcClient;

    /**
     * CONSTRUTOR.
     * <p>
     * - faz a injeção de dependencia no construtor da classe
     *
     * @param jdbcClient -> acessa as metodos para criação de query
     */
    public JdbcUserTypeRepository(final JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * METODO SAVE.
     * <p>
     * - Insere os valores recebidos em cada coluna da tabela user_type
     * - Atraves do parametros nomeados
     *
     * @param jdbcUserTypeEntity -> parametro para acessar os metodos Getters and Setters
     * @return Generated id
     */
    public Long save(final JdbcUserTypeEntity jdbcUserTypeEntity) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql("""
                        INSERT INTO user_type
                            (name, description, created_at, updated_at) 
                        VALUES 
                            (:name, :description, now(), now())
                        """)

                .param("name", jdbcUserTypeEntity.getName())
                .param("description", jdbcUserTypeEntity.getDescription())
                .update(keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        Integer id = (Integer) Objects.requireNonNull(keys).get("id");
        return Long.valueOf(id.toString());
    }

    /**
     * METODO DEACTIVATE.
     * <p>
     * - Ao inves de deletar o usuario da base de dados;
     * - Ateramos seus estado de ativação;
     * - Esse metodo muda o estado de ativação para para desativado = true;
     * - OBS: por padrão o estado de ativação é true;
     *
     * @param id -> Long ID do usuario a ser desativado
     */
    public void deactivate(final Long id) {
        jdbcClient.sql("""
                        UPDATE user_type SET is_active = :active, updated_at = :update WHERE id = :id
                        """)
                .param("id", id) // id do tipo do usuario a ser desativado;
                .param("active", false) // muda o estado de ativado = true por padrão para false;
                .param("update", now())// pega o horario da maquina, para armazenar a hora da modificação;
                .update();// atualiza a tabela user_type

    }

    /**
     * METODO REACTIVATE.
     * <p>
     * - Ao inves de deletar o usuario da base de dados;
     * - Ateramos seu estado de ativação;
     * - Esse metodo muda o estado de ativação para ativo = true;
     * - OBS: por padrão o estado de ativação é true;
     *
     * @param id -> Long ID do usuario a ser desativado
     */
    public void reactivate(final Long id) {
        jdbcClient.sql("""
                        UPDATE user_type SET is_active = :activate, updated_at = now() WHERE id = :id
                        """)
                .param("id", id)// id do tipo do usuario a ser desativado;
                .param("activate", true) // muda o estado de ativado = false para true;
                .update(); // atualiza a tabela no banco
    }

    /**
     * METODO UPDATE.
     * <p>
     * - Atualiza as informações das colunas da tabela user_type;
     * - Atraves de variaveis nomeadas;
     * - jdbcCliente faz a atualização dos dados no final;
     *
     * @param jdbcUserTypeEntity -> parametro para acessar os metodos Getters and Setters
     */
    public void update(final JdbcUserTypeEntity jdbcUserTypeEntity) {
        jdbcClient.sql("""
                        UPDATE user_type 
                        SET 
                            name = :name, 
                            description = :description, 
                            updated_at = now() 
                            WHERE id = :id
                        """)
                .param("name", jdbcUserTypeEntity.getName()) // pega o nome da entidade UserType
                .param("description", jdbcUserTypeEntity.getDescription()) // pega a descrição da entidade UserType
                .param("id", jdbcUserTypeEntity.getId())// pega o ID informado
                .update();// Atualiza as informações na tabela
    }

    /**
     * METODO FINDALL.
     * <p>
     * - seleciona todos os dados da base de dados;
     * - trazendo de pouco em pouco atraves da busca paginada definindo um limit e offset;
     * - coloca os dados dentro de uma lista
     *
     * @param size   -> quantidade de elementos a serem considerados
     * @param offset -> ponto de partida (deslocamento) dentro desse bloco ou conjunto de dados.
     * @return -> uma lista de tipos de usuario
     */
    public List<UserType> findAll(final int size, final int offset) {
        return jdbcClient.sql("""
                    SELECT id, name, description, is_active AS active, 
                           created_at AS created, updated_at AS updated 
                    FROM user_type 
                    LIMIT ? OFFSET ?
                    """)
                .params(List.of(size, offset))
                .query(UserType.class)
                .list();
    }


    /**
     * METODO FINDBYID.
     * <p>
     * - Recebe como parametro um id para consultar na base de dados
     * - busca na tabela user_type tudo onde tiver o id passado como parametro
     * - retorna um Optional com o objeto encotrado ou null se não encotrar nada
     *
     * @param id -> parametro recebe o id
     * @return -> retorna um optional null ou com o Objeto UserType
     */
    public Optional<UserType> findById(final Long id) {
        return jdbcClient.sql("""
                        
                        SELECT id, name, description, is_active as active, created_at as created, updated_at as updated FROM user_type WHERE id = :id
                        
                        """)
                .param("id", id)
                .query(UserType.class)
                .optional();
    }

    public boolean existsByName(String name) {
        return jdbcClient.sql("""
                        
                        SELECT 1 FROM user_type WHERE name = :name
                        
                        """)
                .param("name", name)
                .query()
                .optionalValue()
                .isPresent();
    }

    /**
     * METODO NOW.
     * <p>
     * - Pega a hora da maquina no momento que é chamado
     *
     * @return O horario exato da maquina;
     */
    private Timestamp now() {
        return Timestamp.from(Instant.now());
    }

    public Optional<UserType> findByName(String userTypeName) {
        return jdbcClient.sql("""
                        
                        SELECT * FROM user_type WHERE name = :name
                        
                        """)
                .param("name", userTypeName)
                .query(UserType.class)
                .optional();
    }
}
