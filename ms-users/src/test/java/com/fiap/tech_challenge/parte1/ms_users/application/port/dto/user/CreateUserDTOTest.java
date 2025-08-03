package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserDTOTest {

    @Test
    void testCreateUserDTO() {
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
        
        UsersResponseDTO userResponse = new UsersResponseDTO(userId, name, email, login, addresses);
        String tokenJWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        
        // Act
        CreateUserDTO createUserDTO = new CreateUserDTO(userResponse, tokenJWT);
        
        // Assert
        assertThat(createUserDTO.user()).isEqualTo(userResponse);
        assertThat(createUserDTO.tokenJWT()).isEqualTo(tokenJWT);
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
        
        UsersResponseDTO userResponse = new UsersResponseDTO(userId, name, email, login, addresses);
        String tokenJWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        
        // Act
        CreateUserDTO createUserDTO1 = new CreateUserDTO(userResponse, tokenJWT);
        CreateUserDTO createUserDTO2 = new CreateUserDTO(userResponse, tokenJWT);
        CreateUserDTO differentDTO = new CreateUserDTO(userResponse, "different-token");
        
        // Assert
        assertThat(createUserDTO1).isEqualTo(createUserDTO2);
        assertThat(createUserDTO1.hashCode()).isEqualTo(createUserDTO2.hashCode());
        
        assertThat(createUserDTO1).isNotEqualTo(differentDTO);
        assertThat(createUserDTO1.hashCode()).isNotEqualTo(differentDTO.hashCode());
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
        
        UsersResponseDTO userResponse = new UsersResponseDTO(userId, name, email, login, addresses);
        String tokenJWT = "token";
        
        // Act
        CreateUserDTO createUserDTO = new CreateUserDTO(userResponse, tokenJWT);
        
        // Assert
        String toString = createUserDTO.toString();
        assertThat(toString).contains("CreateUserDTO");
        assertThat(toString).contains(userResponse.toString());
        assertThat(toString).contains(tokenJWT);
    }
}