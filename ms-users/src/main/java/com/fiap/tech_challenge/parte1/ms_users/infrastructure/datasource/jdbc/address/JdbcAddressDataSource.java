package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.AddressDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.AddressMapper;

import java.util.List;
import java.util.UUID;

public class JdbcAddressDataSource implements AddressDataSource {

    private final JdbcAddressRepository jdbcAddressRepository;
    private final AddressMapper addressMapper;

    public JdbcAddressDataSource(JdbcAddressRepository jdbcAddressRepository, AddressMapper addressMapper) {
        this.jdbcAddressRepository = jdbcAddressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public void save(List<Address> addresses, UUID generatedUserId) {
        updateAddressList(addresses, generatedUserId);
    }

    @Override
    public void update(List<Address> addresses, UUID id) {
        updateAddressList(addresses, id);
    }

    private void updateAddressList(List<Address> addresses, UUID id) {
        List<JdbcAddressEntity> jdbcAddressEntity = addressMapper.toJdbcAddressEntity(addresses);
        jdbcAddressRepository.save(jdbcAddressEntity, id);
    }


}
