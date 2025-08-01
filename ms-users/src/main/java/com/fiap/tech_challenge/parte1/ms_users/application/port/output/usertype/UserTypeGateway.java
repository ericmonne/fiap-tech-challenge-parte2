package com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

import java.util.List;
import java.util.Optional;

public interface UserTypeGateway {

    void createUserType(final UserType userType);

    void deactivate(final Long id);

    void reactivate(final Long id);

    void update(final UserType userType);

    List<UserType> findAll(final int size, int offset);

    Optional<UserType> findById(final Long id);

    boolean existsByName(String name);
}
