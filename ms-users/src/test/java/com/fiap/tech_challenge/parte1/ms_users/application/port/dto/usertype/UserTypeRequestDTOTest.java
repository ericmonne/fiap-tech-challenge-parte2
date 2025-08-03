package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTypeRequestDTOTest {

    @Test
    void testUserTypeRequestDTO() {
        // Arrange
        String name = "CUSTOMER";
        String description = "Regular customer user";
        
        // Act
        UserTypeRequestDTO userTypeRequest = new UserTypeRequestDTO(name, description);
        
        // Assert
        assertThat(userTypeRequest.name()).isEqualTo(name);
        assertThat(userTypeRequest.description()).isEqualTo(description);
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        String name = "CUSTOMER";
        String description = "Regular customer user";
        
        // Act
        UserTypeRequestDTO userTypeRequest1 = new UserTypeRequestDTO(name, description);
        UserTypeRequestDTO userTypeRequest2 = new UserTypeRequestDTO(name, description);
        UserTypeRequestDTO differentRequest = new UserTypeRequestDTO("ADMIN", description);
        
        // Assert
        assertThat(userTypeRequest1).isEqualTo(userTypeRequest2);
        assertThat(userTypeRequest1.hashCode()).isEqualTo(userTypeRequest2.hashCode());
        
        assertThat(userTypeRequest1).isNotEqualTo(differentRequest);
        assertThat(userTypeRequest1.hashCode()).isNotEqualTo(differentRequest.hashCode());
    }
    
    @Test
    void testToString() {
        // Arrange
        String name = "CUSTOMER";
        String description = "Regular customer user";
        
        // Act
        UserTypeRequestDTO userTypeRequest = new UserTypeRequestDTO(name, description);
        
        // Assert
        String toString = userTypeRequest.toString();
        assertThat(toString).contains("UserTypeRequestDTO");
        assertThat(toString).contains(name);
        assertThat(toString).contains(description);
    }
    
    @Test
    void testWithDifferentValues() {
        // Arrange
        String name1 = "CUSTOMER";
        String description1 = "Regular customer user";
        
        String name2 = "ADMIN";
        String description2 = "Administrator with full access";
        
        // Act
        UserTypeRequestDTO customerType = new UserTypeRequestDTO(name1, description1);
        UserTypeRequestDTO adminType = new UserTypeRequestDTO(name2, description2);
        
        // Assert
        assertThat(customerType.name()).isEqualTo(name1);
        assertThat(customerType.description()).isEqualTo(description1);
        
        assertThat(adminType.name()).isEqualTo(name2);
        assertThat(adminType.description()).isEqualTo(description2);
        
        assertThat(customerType).isNotEqualTo(adminType);
    }
}