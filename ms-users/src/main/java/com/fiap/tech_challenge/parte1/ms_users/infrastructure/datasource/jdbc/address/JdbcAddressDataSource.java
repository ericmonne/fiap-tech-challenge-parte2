package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address;

import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.address.IAddressMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class JdbcAddressDataSource implements AddressDataSource {

    private final JdbcAddressRepository jdbcAddressRepository;
    private final IAddressMapper iAddressMapper;

    public JdbcAddressDataSource(JdbcAddressRepository jdbcAddressRepository, IAddressMapper iAddressMapper) {
        this.jdbcAddressRepository = jdbcAddressRepository;
        this.iAddressMapper = iAddressMapper;
    }

    @Override
    public void saveUserAddress(List<Address> addresses, UUID generatedUserId) {
        updateAddressList(addresses, generatedUserId);
    }

    @Override
    public void updateUserAddress(List<Address> addresses, UUID id) {
        updateAddressList(addresses, id);
    }

    @Override
    public List<Address> findAllByUserIds(Set<UUID> userIdSet) {
        return jdbcAddressRepository.findAllByUserIds(userIdSet);
    }

    @Override
    public Optional<Address> findByRestaurantId(UUID restaurantId) {
        return jdbcAddressRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public void saveRestaurantAddress(Address address, UUID restaurantId) {
        jdbcAddressRepository.saveRestaurantAddress(address, restaurantId);
    }

    private void updateAddressList(List<Address> addresses, UUID id) {
        List<JdbcAddressEntity> jdbcAddressEntity = iAddressMapper.toJdbcAddressEntity(addresses);
        jdbcAddressRepository.saveUserAddress(jdbcAddressEntity, id);
    }

}
