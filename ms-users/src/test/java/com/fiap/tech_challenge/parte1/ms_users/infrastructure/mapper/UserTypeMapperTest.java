package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype.UserTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.usertype.JdbcUserTypeEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeMapperTest {

    private final UserTypeMapper mapper = new UserTypeMapper();

    @Test
    void tojdbcUserTypeEntity_shouldMapAllFields() {

        UserType userType = new UserType();
        userType.setName("Admin");
        userType.setDescription("Administrator");
        userType.setActive(true);

        JdbcUserTypeEntity jdbcEntity = mapper.tojdbcUserTypeEntity(userType);

        assertEquals("Admin", jdbcEntity.getName());
        assertEquals("Administrator", jdbcEntity.getDescription());
        assertTrue(jdbcEntity.getActive());
    }

    @Test
    void toUserType_shouldMapAllFieldsFromJdbcEntity() {
        JdbcUserTypeEntity jdbcEntity = new JdbcUserTypeEntity();
        jdbcEntity.setName("User");
        jdbcEntity.setDescription("Regular user");
        jdbcEntity.setActive(false);

        UserType userType = mapper.toUserType(jdbcEntity);

        assertEquals("User", userType.getName());
        assertEquals("Regular user", userType.getDescription());
        assertFalse(userType.getActive());
    }

    @Test
    void toUserType_shouldMapFromRequestDto() {
        UserTypeRequestDTO dto = new UserTypeRequestDTO("Guest", "Guest user");

        UserType userType = mapper.toUserType(dto);

        assertNull(userType.getId());
        assertEquals("Guest", userType.getName());
        assertEquals("Guest user", userType.getDescription());
    }

    @Test
    void toUserType_withId_shouldMapFromRequestDtoAndSetId() {
        UserTypeRequestDTO dto = new UserTypeRequestDTO("Manager", "Manager user");
        Long id = 42L;

        UserType userType = mapper.toUserType(dto, id);

        assertEquals(id, userType.getId());
        assertEquals("Manager", userType.getName());
        assertEquals("Manager user", userType.getDescription());
    }

    @Test
    void toUserTypeResponseDto_shouldMapAllFields() {
        UserType userType = new UserType();
        userType.setName("Support");
        userType.setDescription("Support staff");
        userType.setActive(true);

        UserTypeResponseDTO dto = mapper.toUserTypeResponseDto(userType);

        assertEquals("Support", dto.name());
        assertEquals("Support staff", dto.description());
        assertTrue(dto.active());
    }

    @Test
    void toListUserTypeResponseDto_shouldMapListCorrectly() {
        UserType ut1 = new UserType();
        ut1.setName("Admin");
        ut1.setDescription("Administrator");
        ut1.setActive(true);

        UserType ut2 = new UserType();
        ut2.setName("User");
        ut2.setDescription("Regular user");
        ut2.setActive(false);

        List<UserTypeResponseDTO> dtos = mapper.toListUserTypeResponseDto(List.of(ut1, ut2));

        assertEquals(2, dtos.size());
        assertEquals(ut1.getName(), dtos.get(0).name());
        assertEquals(ut2.getName(), dtos.get(1).name());
    }
}
