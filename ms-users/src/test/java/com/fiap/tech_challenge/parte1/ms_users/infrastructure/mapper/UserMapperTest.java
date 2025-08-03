package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UpdateUserDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UsersRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.UsersResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.address.IAddressMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserMapperTest {

    private IAddressMapper addressMapper;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        addressMapper = mock(IAddressMapper.class);
        userMapper = new UserMapper(addressMapper);
    }

    @Test
    void toResponseDTO_shouldMapUserToUsersResponseDTO() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setLogin("johndoe");
        user.setAddress(List.of(new Address()));

        List<AddressResponseDTO> mockedAddressResponse = List.of(new AddressResponseDTO(
                UUID.randomUUID(), "12345", "Street", 100, "Comp", "Neighborhood", "City", "State"
        ));
        when(addressMapper.toAddressResponseDTO(anyList())).thenReturn(mockedAddressResponse);

        UsersResponseDTO dto = userMapper.toResponseDTO(user);

        assertEquals(userId, dto.id());
        assertEquals("John Doe", dto.name());
        assertEquals("john@example.com", dto.email());
        assertEquals("johndoe", dto.login());
        assertEquals(mockedAddressResponse, dto.address());
        verify(addressMapper).toAddressResponseDTO(user.getAddress());
    }

    @Test
    void toResponseDTO_shouldMapListOfUsers() {
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setAddress(List.of(new Address()));

        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setAddress(List.of(new Address()));

        when(addressMapper.toAddressResponseDTO(anyList())).thenReturn(List.of());

        List<UsersResponseDTO> dtos = userMapper.toResponseDTO(List.of(user1, user2));

        assertEquals(2, dtos.size());
    }

    @Test
    void toJdbcUserEntity_shouldMapAllFields() {
        User user = new User();
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        user.setName("Name");
        user.setEmail("email@example.com");
        user.setLogin("login");
        user.setActive(true);
        UserType userType = new UserType();
        userType.setId(1L);
        user.setUserType(userType);
        user.setPassword("pwd");
        user.setDateLastChange(new Date());

        JdbcUserEntity jdbcUserEntity = userMapper.toJdbcUserEntity(user);

        assertEquals(userId, jdbcUserEntity.getId());
        assertEquals(user.getName(), jdbcUserEntity.getName());
        assertEquals(user.getEmail(), jdbcUserEntity.getEmail());
        assertEquals(user.getLogin(), jdbcUserEntity.getLogin());
        assertEquals(user.getActive(), jdbcUserEntity.getActive());
        assertEquals(user.getPassword(), jdbcUserEntity.getPassword());
    }

    @Test
    void toEntity_shouldMapUsersRequestDTOToUser() {
        AddressRequestDTO addressRequestDTO = new AddressRequestDTO(
                "12345", "Street", 10, "Comp", "Neighborhood", "City", "ST"
        );

        UsersRequestDTO usersRequestDTO = new UsersRequestDTO(
                "John Doe",
                "john@example.com",
                "johndoe",
                "pwd",
                "ADMIN",
                List.of(addressRequestDTO)
        );

        List<Address> mockedAddresses = List.of(new Address());
        when(addressMapper.toEntity(usersRequestDTO.address())).thenReturn(mockedAddresses);

        User user = userMapper.toEntity(usersRequestDTO);

        assertEquals(usersRequestDTO.email(), user.getEmail());
        assertEquals(usersRequestDTO.name(), user.getName());
        assertEquals(usersRequestDTO.login(), user.getLogin());
        assertEquals(usersRequestDTO.password(), user.getPassword());
        assertEquals("ADMIN", user.getUserType().getName());
        assertTrue(user.getActive());
        assertEquals(mockedAddresses, user.getAddress());

        verify(addressMapper).toEntity(usersRequestDTO.address());
    }

    @Test
    void toEntity_updateUserDTO_shouldMapFieldsAndUserId() {
        UpdateUserDTO updateUserDTO = new UpdateUserDTO(
                "John Updated",
                "johnupdated@example.com",
                "johnupdated",
                List.of(new AddressRequestDTO("11111", "Street2", 20, "Comp2", "Neighborhood2", "City2", "ST2"))
        );
        UUID userId = UUID.randomUUID();

        List<Address> mockedAddresses = List.of(new Address());
        when(addressMapper.toEntity(updateUserDTO.address())).thenReturn(mockedAddresses);

        User user = userMapper.toEntity(updateUserDTO, userId);

        assertEquals(userId, user.getId());
        assertEquals(updateUserDTO.email(), user.getEmail());
        assertEquals(updateUserDTO.name(), user.getName());
        assertEquals(updateUserDTO.login(), user.getLogin());
        assertEquals(mockedAddresses, user.getAddress());

        verify(addressMapper).toEntity(updateUserDTO.address());
    }
}
