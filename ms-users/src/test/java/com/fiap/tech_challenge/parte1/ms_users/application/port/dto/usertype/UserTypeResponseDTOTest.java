package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTypeResponseDTOTest {

    @Test
    void testUserTypeResponseDTO() {
        // Arrange
        Long id = 1L;
        String name = "CUSTOMER";
        String description = "Regular customer user";
        Boolean active = true;
        
        // Act
        UserTypeResponseDTO userTypeResponse = new UserTypeResponseDTO(id, name, description, active);
        
        // Assert
        assertThat(userTypeResponse.id()).isEqualTo(id);
        assertThat(userTypeResponse.name()).isEqualTo(name);
        assertThat(userTypeResponse.description()).isEqualTo(description);
        assertThat(userTypeResponse.active()).isEqualTo(active);
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Long id = 1L;
        String name = "CUSTOMER";
        String description = "Regular customer user";
        Boolean active = true;
        
        // Act
        UserTypeResponseDTO userTypeResponse1 = new UserTypeResponseDTO(id, name, description, active);
        UserTypeResponseDTO userTypeResponse2 = new UserTypeResponseDTO(id, name, description, active);
        UserTypeResponseDTO differentResponse = new UserTypeResponseDTO(id, "ADMIN", description, active);
        
        // Assert
        assertThat(userTypeResponse1).isEqualTo(userTypeResponse2);
        assertThat(userTypeResponse1.hashCode()).isEqualTo(userTypeResponse2.hashCode());
        
        assertThat(userTypeResponse1).isNotEqualTo(differentResponse);
        assertThat(userTypeResponse1.hashCode()).isNotEqualTo(differentResponse.hashCode());
    }
    
    @Test
    void testToString() {
        // Arrange
        Long id = 1L;
        String name = "CUSTOMER";
        String description = "Regular customer user";
        Boolean active = true;
        
        // Act
        UserTypeResponseDTO userTypeResponse = new UserTypeResponseDTO(id, name, description, active);
        
        // Assert
        String toString = userTypeResponse.toString();
        assertThat(toString).contains("UserTypeResponseDTO");
        assertThat(toString).contains(id.toString());
        assertThat(toString).contains(name);
        assertThat(toString).contains(description);
        assertThat(toString).contains(active.toString());
    }
    
    @Test
    void testWithInactiveStatus() {
        // Arrange
        Long id = 1L;
        String name = "CUSTOMER";
        String description = "Regular customer user";
        Boolean active = false;
        
        // Act
        UserTypeResponseDTO userTypeResponse = new UserTypeResponseDTO(id, name, description, active);
        
        // Assert
        assertThat(userTypeResponse.id()).isEqualTo(id);
        assertThat(userTypeResponse.name()).isEqualTo(name);
        assertThat(userTypeResponse.description()).isEqualTo(description);
        assertThat(userTypeResponse.active()).isFalse();
    }
}