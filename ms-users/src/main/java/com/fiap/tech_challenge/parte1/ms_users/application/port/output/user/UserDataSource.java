package com.fiap.tech_challenge.parte1.ms_users.application.port.output.user;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDataSource {
    UUID createUser(User user);

    Optional<User> findById(UUID userId);

    void update(User user);

    boolean existsById(UUID id);

    boolean emailAlreadyExistsForDifferentUsers(String email, UUID userId);

    boolean loginAlreadyExistsForDifferentUsers(String login, UUID userId);

    List<User> findAll(int size, int offset);

    void reactivate(UUID id);

    void deactivate(UUID id);

    void changePassword(UUID id, String newPasswordEncoded);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    Optional<User> findByLogin(String username);
}
