package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AddressRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address.JdbcAddressEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper component responsible for converting {@link Address} entities
 * to {@link AddressResponseDTO} data transfer objects.
 */
@Component
public class AddressMapper {

    /**
     * Converts a single {@link Address} entity to an {@link AddressResponseDTO}.
     *
     * @param address the {@link Address} entity to convert
     * @return the corresponding {@link AddressResponseDTO}
     */
    public AddressResponseDTO toAddressRequestDTO(Address address) {
        return new AddressResponseDTO(
                address.getId(),
                address.getZipcode(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getUserId());
    }

    /**
     * Converts a list of {@link Address} entities to a list of {@link AddressResponseDTO}s.
     *
     * @param addresses the list of {@link Address} entities to convert
     * @return a list of corresponding {@link AddressResponseDTO}s
     */
    public List<AddressResponseDTO> toAddressRequestDTO(List<Address> addresses) {
        return addresses.stream()
                .map(this::toAddressRequestDTO)
                .toList();
    }

    public List<Address> toEntity(@Valid @NotEmpty(message = "User must have at least one Address") List<AddressRequestDTO> address) {
        List<Address> addresses = new ArrayList<>();
        for (AddressRequestDTO addressRequestDTO : address) {
            Address addressEntity = new Address();
            addressEntity.setZipcode(addressRequestDTO.zipcode());
            addressEntity.setStreet(addressRequestDTO.street());
            addressEntity.setNumber(addressRequestDTO.number());
            addressEntity.setComplement(addressRequestDTO.complement());
            addressEntity.setNeighborhood(addressRequestDTO.neighborhood());
            addressEntity.setCity(addressRequestDTO.city());
            addressEntity.setState(addressRequestDTO.state());
            addresses.add(addressEntity);
        }
        return addresses;
    }

    public List<JdbcAddressEntity> toJdbcAddressEntity(List<Address> addresses) {
        return addresses.stream().map(this::toJdbcAddressEntity).toList();
    }

    private JdbcAddressEntity toJdbcAddressEntity(Address address) {
        JdbcAddressEntity jdbcAddressEntity = new JdbcAddressEntity();
        jdbcAddressEntity.setId(address.getId());
        jdbcAddressEntity.setZipcode(address.getZipcode());
        jdbcAddressEntity.setStreet(address.getStreet());
        jdbcAddressEntity.setNumber(address.getNumber());
        jdbcAddressEntity.setComplement(address.getComplement());
        jdbcAddressEntity.setNeighborhood(address.getNeighborhood());
        jdbcAddressEntity.setCity(address.getCity());
        jdbcAddressEntity.setState(address.getState());
        return jdbcAddressEntity;
    }

}
