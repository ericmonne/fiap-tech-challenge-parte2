package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address.JdbcAddressEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private AddressMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AddressMapper();
    }

    @Test
    void toAddressResponseDTO_shouldMapAllFields() {
        Address address = createAddress();
        AddressResponseDTO dto = mapper.toAddressResponseDTO(address);

        assertEquals(address.getId(), dto.id());
        assertEquals(address.getZipcode(), dto.zipcode());
        assertEquals(address.getStreet(), dto.street());
        assertEquals(address.getNumber(), dto.number());
        assertEquals(address.getComplement(), dto.complement());
        assertEquals(address.getNeighborhood(), dto.neighborhood());
        assertEquals(address.getCity(), dto.city());
        assertEquals(address.getState(), dto.state());
    }

    @Test
    void toAddressResponseDTO_list_shouldMapAllAddresses() {
        Address address1 = createAddress();
        Address address2 = createAddress();

        List<AddressResponseDTO> dtos = mapper.toAddressResponseDTO(List.of(address1, address2));
        assertEquals(2, dtos.size());

        assertEquals(address1.getId(), dtos.get(0).id());
        assertEquals(address2.getId(), dtos.get(1).id());
    }

    @Test
    void toEntity_fromAddressRequestDTO_shouldMapAllFields() {
        AddressRequestDTO dto = new AddressRequestDTO(
                "12345-678",
                "Rua A",
                100,
                "Apto 1",
                "Bairro B",
                "Cidade C",
                "SP"
        );

        Address entity = mapper.toEntity(dto);

        assertNull(entity.getId()); // id não é setado
        assertEquals(dto.zipcode(), entity.getZipcode());
        assertEquals(dto.street(), entity.getStreet());
        assertEquals(dto.number(), entity.getNumber());
        assertEquals(dto.complement(), entity.getComplement());
        assertEquals(dto.neighborhood(), entity.getNeighborhood());
        assertEquals(dto.city(), entity.getCity());
        assertEquals(dto.state(), entity.getState());
    }

    @Test
    void toEntity_list_shouldMapAllAddressRequestDTOs() {
        AddressRequestDTO dto1 = new AddressRequestDTO("11111-111", "Street 1", 1, "C1", "N1", "City1", "ST1");
        AddressRequestDTO dto2 = new AddressRequestDTO("22222-222", "Street 2", 2, "C2", "N2", "City2", "ST2");

        List<Address> entities = mapper.toEntity(List.of(dto1, dto2));
        assertEquals(2, entities.size());

        assertEquals(dto1.zipcode(), entities.get(0).getZipcode());
        assertEquals(dto2.zipcode(), entities.get(1).getZipcode());
    }

    @Test
    void toJdbcAddressEntity_shouldMapAllFields() {
        Address address = createAddress();
        List<JdbcAddressEntity> jdbcEntities = mapper.toJdbcAddressEntity(List.of(address));

        assertEquals(1, jdbcEntities.size());
        JdbcAddressEntity jdbc = jdbcEntities.get(0);

        assertEquals(address.getId(), jdbc.getId());
        assertEquals(address.getZipcode(), jdbc.getZipcode());
        assertEquals(address.getStreet(), jdbc.getStreet());
        assertEquals(address.getNumber(), jdbc.getNumber());
        assertEquals(address.getComplement(), jdbc.getComplement());
        assertEquals(address.getNeighborhood(), jdbc.getNeighborhood());
        assertEquals(address.getCity(), jdbc.getCity());
        assertEquals(address.getState(), jdbc.getState());
    }

    private Address createAddress() {
        Address address = new Address();
        address.setId(UUID.randomUUID());
        address.setZipcode("12345-678");
        address.setStreet("Rua Teste");
        address.setNumber(123);
        address.setComplement("Apto 101");
        address.setNeighborhood("Centro");
        address.setCity("Cidade");
        address.setState("ST");
        return address;
    }
}
