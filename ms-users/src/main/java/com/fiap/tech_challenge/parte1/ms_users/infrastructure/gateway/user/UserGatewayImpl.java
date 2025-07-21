package com.fiap.tech_challenge.parte1.ms_users.infrastructure.gateway.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserGatewayImpl implements UserGateway {

    private final UserDataSource userDataSource;

    public UserGatewayImpl(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }


    @Override
    public UUID createUser(User user) {
        return userDataSource.createUser(user);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userDataSource.findById(userId);
    }

    @Override
    public void update(User user) {
        userDataSource.update(user);
    }

    @Override
    public boolean existsById(UUID id) {
        return userDataSource.existsById(id);
    }

    @Override
    public boolean emailAlreadyExistsForDifferentUsers(String email, UUID userId) {
        return userDataSource.emailAlreadyExistsForDifferentUsers(email, userId);
    }

    @Override
    public boolean loginAlreadyExistsForDifferentUsers(String login, UUID userId) {
        return userDataSource.loginAlreadyExistsForDifferentUsers(login, userId);
    }

    @Override
    public List<User> findAll(int size, int offset) {
        return userDataSource.findAll(size, offset);
    }
}
