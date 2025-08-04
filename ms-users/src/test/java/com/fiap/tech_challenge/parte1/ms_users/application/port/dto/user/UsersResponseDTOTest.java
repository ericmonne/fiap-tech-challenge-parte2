package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UsersResponseDTOTest {

    @Test
    void testUsersResponseDTO() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";

        List<AddressResponseDTO> addresses = new ArrayList<>();
        UUID addressId = UUID.randomUUID();
        AddressResponseDTO address = new AddressResponseDTO(
                addressId,
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
        UsersResponseDTO userResponse = new UsersResponseDTO(userId, name, email, login, addresses);

        // Assert
        assertThat(userResponse.id()).isEqualTo(userId);
        assertThat(userResponse.name()).isEqualTo(name);
        assertThat(userResponse.email()).isEqualTo(email);
        assertThat(userResponse.login()).isEqualTo(login);
        assertThat(userResponse.address()).isEqualTo(addresses);
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";

        List<AddressResponseDTO> addresses = new ArrayList<>();
        UUID addressId = UUID.randomUUID();
        AddressResponseDTO address = new AddressResponseDTO(
                addressId,
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
        UsersResponseDTO userResponse1 = new UsersResponseDTO(userId, name, email, login, addresses);
        UsersResponseDTO userResponse2 = new UsersResponseDTO(userId, name, email, login, addresses);
        UsersResponseDTO differentResponse = new UsersResponseDTO(userId, "Different Name", email, login, addresses);

        // Assert
        assertThat(userResponse1).isEqualTo(userResponse2);
        assertThat(userResponse1.hashCode()).isEqualTo(userResponse2.hashCode());

        assertThat(userResponse1).isNotEqualTo(differentResponse);
        assertThat(userResponse1.hashCode()).isNotEqualTo(differentResponse.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";

        List<AddressResponseDTO> addresses = new ArrayList<>();
        UUID addressId = UUID.randomUUID();
        AddressResponseDTO address = new AddressResponseDTO(
                addressId,
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
        UsersResponseDTO userResponse = new UsersResponseDTO(userId, name, email, login, addresses);

        // Assert
        String toString = userResponse.toString();
        assertThat(toString).contains("UsersResponseDTO");
        assertThat(toString).contains(userId.toString());
        assertThat(toString).contains(name);
        assertThat(toString).contains(email);
        assertThat(toString).contains(login);
        assertThat(toString).contains(addresses.toString());
    }

    @Test
    void testWithEmptyAddressList() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String name = "John Doe";
        String email = "john.doe@example.com";
        String login = "johndoe";
        List<AddressResponseDTO> emptyAddresses = new ArrayList<>();

        // Act
        UsersResponseDTO userResponse = new UsersResponseDTO(userId, name, email, login, emptyAddresses);

        // Assert
        assertThat(userResponse.id()).isEqualTo(userId);
        assertThat(userResponse.name()).isEqualTo(name);
        assertThat(userResponse.email()).isEqualTo(email);
        assertThat(userResponse.login()).isEqualTo(login);
        assertThat(userResponse.address()).isEmpty();
    }
}