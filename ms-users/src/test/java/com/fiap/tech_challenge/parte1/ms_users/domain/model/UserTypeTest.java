package com.fiap.tech_challenge.parte1.ms_users.domain.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class UserTypeTest {

    @Test
    void testParameterizedConstructor() {
        // Arrange
        String name = "Customer";
        String description = "Regular customer user";
        Boolean active = true;
        
        // Act
        UserType userType = new UserType(name, description, active);
        
        // Assert
        assertThat(userType.getName()).isEqualTo(name);
        assertThat(userType.getDescription()).isEqualTo(description);
        assertThat(userType.getActive()).isTrue();
        assertThat(userType.getCreated()).isNotNull();
        assertThat(userType.getUpdated()).isNotNull();
        assertThat(userType.getId()).isNull();
    }
    
    @Test
    void testDefaultConstructor() {
        // Act
        UserType userType = new UserType();
        
        // Assert
        assertThat(userType.getId()).isNull();
        assertThat(userType.getName()).isNull();
        assertThat(userType.getDescription()).isNull();
        assertThat(userType.getActive()).isNull();
        assertThat(userType.getCreated()).isNull();
        assertThat(userType.getUpdated()).isNull();
    }
    
    @Test
    void testGettersAndSetters() {
        // Arrange
        UserType userType = new UserType();
        Long id = 1L;
        String name = "Admin";
        String description = "Administrator user with full access";
        Boolean active = true;
        Date created = new Date();
        Date updated = new Date();
        
        // Act
        userType.setId(id);
        userType.setName(name);
        userType.setDescription(description);
        userType.setActive(active);
        userType.setCreated(created);
        userType.setUpdated(updated);
        
        // Assert
        assertThat(userType.getId()).isEqualTo(id);
        assertThat(userType.getName()).isEqualTo(name);
        assertThat(userType.getDescription()).isEqualTo(description);
        assertThat(userType.getActive()).isEqualTo(active);
        assertThat(userType.getCreated()).isEqualTo(created);
        assertThat(userType.getUpdated()).isEqualTo(updated);
    }
    
    @Test
    void testActiveDefaultValueInParameterizedConstructor() {
        // Arrange
        String name = "Staff";
        String description = "Staff member with limited access";
        Boolean active = false; // This should be overridden to true in constructor
        
        // Act
        UserType userType = new UserType(name, description, active);
        
        // Assert
        assertThat(userType.getActive()).isTrue(); // Should always be true regardless of input
    }
}