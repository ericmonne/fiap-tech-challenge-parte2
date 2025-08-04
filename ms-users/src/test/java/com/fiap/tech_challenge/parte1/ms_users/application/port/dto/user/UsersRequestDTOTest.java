package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressRequestDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UsersRequestDTOTest {

    @Test
    void testUsersRequestDTO() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";
        String password = "password123";
        String userType = "CUSTOMER";

        List<AddressRequestDTO> addresses = new ArrayList<>();
        AddressRequestDTO address = new AddressRequestDTO(
                "12345-678",
                "Main Street",
                123,
                "Apt 4B",
                "Downtown",
                "New York",
                "NY"
        );
        addresses.add(address);

        // Act
        UsersRequestDTO userRequest = new UsersRequestDTO(name, email, login, password, userType, addresses);

        // Assert
        assertThat(userRequest.name()).isEqualTo(name);
        assertThat(userRequest.email()).isEqualTo(email);
        assertThat(userRequest.login()).isEqualTo(login);
        assertThat(userRequest.password()).isEqualTo(password);
        assertThat(userRequest.userType()).isEqualTo(userType);
        assertThat(userRequest.address()).isEqualTo(addresses);
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";
        String password = "password123";
        String userType = "CUSTOMER";

        List<AddressRequestDTO> addresses = new ArrayList<>();
        AddressRequestDTO address = new AddressRequestDTO(
                "12345-678",
                "Main Street",
                123,
                "Apt 4B",
                "Downtown",
                "New York",
                "NY"
        );
        addresses.add(address);

        // Act
        UsersRequestDTO userRequest1 = new UsersRequestDTO(name, email, login, password, userType, addresses);
        UsersRequestDTO userRequest2 = new UsersRequestDTO(name, email, login, password, userType, addresses);
        UsersRequestDTO differentRequest = new UsersRequestDTO("Different Name", email, login, password, userType, addresses);

        // Assert
        assertThat(userRequest1).isEqualTo(userRequest2);
        assertThat(userRequest1.hashCode()).isEqualTo(userRequest2.hashCode());

        assertThat(userRequest1).isNotEqualTo(differentRequest);
        assertThat(userRequest1.hashCode()).isNotEqualTo(differentRequest.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";
        String password = "password123";
        String userType = "CUSTOMER";

        List<AddressRequestDTO> addresses = new ArrayList<>();
        AddressRequestDTO address = new AddressRequestDTO(
                "12345-678",
                "Main Street",
                123,
                "Apt 4B",
                "Downtown",
                "New York",
                "NY"
        );
        addresses.add(address);

        // Act
        UsersRequestDTO userRequest = new UsersRequestDTO(name, email, login, password, userType, addresses);

        // Assert
        String toString = userRequest.toString();
        assertThat(toString).contains("UsersRequestDTO");
        assertThat(toString).contains(name);
        assertThat(toString).contains(email);
        assertThat(toString).contains(login);
        assertThat(toString).contains(password);
        assertThat(toString).contains(userType);
        assertThat(toString).contains(addresses.toString());
    }

    @Test
    void testWithMultipleAddresses() {
        // Arrange
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";
        String password = "password123";
        String userType = "CUSTOMER";

        List<AddressRequestDTO> addresses = new ArrayList<>();

        AddressRequestDTO address1 = new AddressRequestDTO(
                "12345-678",
                "Main Street",
                123,
                "Apt 4B",
                "Downtown",
                "New York",
                "NY"
        );

        AddressRequestDTO address2 = new AddressRequestDTO(
                "90001",
                "Second Street",
                456,
                null,
                "Westside",
                "Los Angeles",
                "CA"
        );

        addresses.add(address1);
        addresses.add(address2);

        // Act
        UsersRequestDTO userRequest = new UsersRequestDTO(name, email, login, password, userType, addresses);

        // Assert
        assertThat(userRequest.address()).hasSize(2);
        assertThat(userRequest.address()).containsExactly(address1, address2);
    }
}