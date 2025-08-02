package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.usertype;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.usertype.UserTypeGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

import java.util.List;
import java.util.Optional;

public class UserTypeGatewayImpl implements UserTypeGateway {

    private final UserTypeDataSource userTypeDataSource;

    public UserTypeGatewayImpl(final UserTypeDataSource userTypeDataSource) {
        this.userTypeDataSource = userTypeDataSource;
    }

    @Override
    public void createUserType(final UserType userType) {
        this.userTypeDataSource.createUserType(userType);
    }

    @Override
    public void deactivate(final Long id) {
        this.userTypeDataSource.deactivate(id);
    }

    @Override
    public void reactivate(final Long id) {
        this.userTypeDataSource.reactivate(id);
    }

    @Override
    public void update(final UserType userType) {
        this.userTypeDataSource.update(userType);
    }

    @Override
    public List<UserType> findAll(final int size, final int offset) {
        return this.userTypeDataSource.findAll(size,offset);
    }

    @Override
    public Optional<UserType> findById(final Long id) {
        return this.userTypeDataSource.findById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return this.userTypeDataSource.existsByName(name);
    }

    @Override
    public Optional<UserType> findByName(String userTypeName) {
        return this.userTypeDataSource.findByName(userTypeName);
    }
}
