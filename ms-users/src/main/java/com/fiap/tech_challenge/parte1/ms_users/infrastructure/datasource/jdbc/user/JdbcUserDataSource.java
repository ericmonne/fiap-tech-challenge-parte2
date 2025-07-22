package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcUserDataSource implements UserDataSource {

    private final JdbcUserRepository jdbcUserRepository;
    private final UserMapper userMapper;

    public JdbcUserDataSource(JdbcUserRepository jdbcUserRepository, UserMapper userMapper) {
        this.jdbcUserRepository = jdbcUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UUID createUser(User user) {
        JdbcUserEntity jdbcUserEntity = userMapper.toJdbcUserEntity(user);
        return jdbcUserRepository.save(jdbcUserEntity);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return jdbcUserRepository.findById(userId);
    }

    @Override
    public void update(User user) {
        JdbcUserEntity jdbcUserEntity = userMapper.toJdbcUserEntity(user);
        jdbcUserRepository.update(jdbcUserEntity);
    }

    @Override
    public boolean existsById(UUID id) {
        return jdbcUserRepository.existsById(id);
    }

    @Override
    public boolean emailAlreadyExistsForDifferentUsers(String email, UUID userId) {
        return jdbcUserRepository.emailAlreadyExistsForDifferentUsers(email, userId);
    }

    @Override
    public boolean loginAlreadyExistsForDifferentUsers(String login, UUID userId) {
        return jdbcUserRepository.loginAlreadyExistsForDifferentUsers(login, userId);
    }

    @Override
    public List<User> findAll(int size, int offset) {
        return jdbcUserRepository.findAll(size, offset);
    }

    @Override
    public void reactivate(UUID id) {
        jdbcUserRepository.reactivate(id);
    }

    @Override
    public void deactivate(UUID id) {
        jdbcUserRepository.deactivate(id);
    }

    @Override
    public void changePassword(UUID id, String newPasswordEncoded) {
        jdbcUserRepository.changePassword(id, newPasswordEncoded);
    }
}
