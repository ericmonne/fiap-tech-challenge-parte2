package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.address.AddressGateway;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address.JdbcAddressDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address.JdbcAddressRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.address.AddressGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.IAddressMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressBeanConfig {

    @Bean
    AddressDataSource registerAddressDataSource(JdbcAddressRepository jdbcAddressRepository, IAddressMapper iAddressMapper) {
        return new JdbcAddressDataSource(jdbcAddressRepository, iAddressMapper);
    }

    @Bean
    public AddressGateway registerAddressGateway(AddressDataSource addressDataSource) {
        return new AddressGatewayImpl(addressDataSource);
    }

}

