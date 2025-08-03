package com.fiap.tech_challenge.parte1.ms_users.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    private UserType userType;
    
    @BeforeEach
    void setUp() {
        userType = new UserType();
        userType.setId(1L);
        userType.setName("Customer");
        userType.setActive(true);
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";
        String password = "password123";
        
        // Act
        User user = new User(name, email, login, password, userType);
        
        // Assert
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getLogin()).isEqualTo(login);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getDateLastChange()).isNotNull();
        assertThat(user.getActive()).isTrue();
        assertThat(user.getUserType()).isEqualTo(userType);
    }
    
    @Test
    void testDefaultConstructor() {
        // Act
        User user = new User();
        
        // Assert
        assertThat(user.getId()).isNull();
        assertThat(user.getName()).isNull();
        assertThat(user.getEmail()).isNull();
        assertThat(user.getLogin()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.getDateLastChange()).isNull();
        assertThat(user.getActive()).isNull();
        assertThat(user.getUserType()).isNull();
        assertThat(user.getAddress()).isNull();
    }
    
    @Test
    void testGettersAndSetters() {
        // Arrange
        User user = new User();
        UUID id = UUID.randomUUID();
        String name = "Jane Doe";
        String email = "jane.doe@example.com";
        String login = "janedoe";
        String password = "securepass";
        Date dateLastChange = new Date();
        Boolean active = true;
        
        // Act
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setDateLastChange(dateLastChange);
        user.setActive(active);
        user.setUserType(userType);
        
        // Assert
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getLogin()).isEqualTo(login);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getDateLastChange()).isEqualTo(dateLastChange);
        assertThat(user.getActive()).isEqualTo(active);
        assertThat(user.getUserType()).isEqualTo(userType);
    }
    
    @Test
    void testAddressManagement() {
        // Arrange
        User user = new User();
        List<Address> addresses = new ArrayList<>();
        
        Address address1 = new Address();
        address1.setId(UUID.randomUUID());
        address1.setStreet("Main Street");
        address1.setNumber(123);
        address1.setCity("New York");
        address1.setState("NY");
        address1.setZipcode("10001");
        
        Address address2 = new Address();
        address2.setId(UUID.randomUUID());
        address2.setStreet("Second Street");
        address2.setNumber(456);
        address2.setCity("Los Angeles");
        address2.setState("CA");
        address2.setZipcode("90001");
        
        addresses.add(address1);
        addresses.add(address2);
        
        // Act
        user.setAddress(addresses);
        
        // Assert
        assertThat(user.getAddress()).isEqualTo(addresses);
        assertThat(user.getAddresses()).isEqualTo(addresses);
        assertThat(user.getMainAddress()).isEqualTo(address1);
    }
    
    @Test
    void testGetMainAddress_shouldThrowException_whenNoAddressesExist() {
        // Arrange
        User user = new User();
        user.setAddress(new ArrayList<>());
        
        // Act & Assert
        assertThatThrownBy(() -> user.getMainAddress())
                .isInstanceOf(IndexOutOfBoundsException.class);
    }
    
    @Test
    void testGetMainAddress_shouldThrowException_whenAddressIsNull() {
        // Arrange
        User user = new User();
        
        // Act & Assert
        assertThatThrownBy(() -> user.getMainAddress())
                .isInstanceOf(NullPointerException.class);
    }
}