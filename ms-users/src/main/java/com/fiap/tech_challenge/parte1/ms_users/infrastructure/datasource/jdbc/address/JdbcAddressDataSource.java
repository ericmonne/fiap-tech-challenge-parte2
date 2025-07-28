package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address;

import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.address.IAddressMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;

import java.util.List;
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
    public void save(List<Address> addresses, UUID generatedUserId) {
        updateAddressList(addresses, generatedUserId);
    }

    @Override
    public void update(List<Address> addresses, UUID id) {
        updateAddressList(addresses, id);
    }

    @Override
    public List<Address> findAllByUserIds(Set<UUID> userIdSet) {
        return jdbcAddressRepository.findAllByUserIds(userIdSet);
    }

    private void updateAddressList(List<Address> addresses, UUID id) {
        List<JdbcAddressEntity> jdbcAddressEntity = iAddressMapper.toJdbcAddressEntity(addresses);
        jdbcAddressRepository.save(jdbcAddressEntity, id);
    }


}
