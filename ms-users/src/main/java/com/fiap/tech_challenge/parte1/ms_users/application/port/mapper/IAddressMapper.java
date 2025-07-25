package com.fiap.tech_challenge.parte1.ms_users.application.port.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AddressRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address.JdbcAddressEntity;

import java.util.List;

public interface IAddressMapper {
    AddressResponseDTO toAddressRequestDTO(Address address);

    List<AddressResponseDTO> toAddressRequestDTO(List<Address> addresses);

    List<Address> toEntity(List<AddressRequestDTO> address);

    List<JdbcAddressEntity> toJdbcAddressEntity(List<Address> addresses);
}
