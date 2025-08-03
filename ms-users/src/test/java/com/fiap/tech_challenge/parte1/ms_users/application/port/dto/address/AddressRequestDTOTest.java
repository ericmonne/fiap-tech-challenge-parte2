package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressRequestDTOTest {

    @Test
    void testAddressRequestDTO() {
        // Arrange
        String zipcode = "12345-678";
        String street = "Main Street";
        Integer number = 123;
        String complement = "Apt 4B";
        String neighborhood = "Downtown";
        String city = "New York";
        String state = "NY";
        
        // Act
        AddressRequestDTO addressRequest = new AddressRequestDTO(
                zipcode, street, number, complement, neighborhood, city, state);
        
        // Assert
        assertThat(addressRequest.zipcode()).isEqualTo(zipcode);
        assertThat(addressRequest.street()).isEqualTo(street);
        assertThat(addressRequest.number()).isEqualTo(number);
        assertThat(addressRequest.complement()).isEqualTo(complement);
        assertThat(addressRequest.neighborhood()).isEqualTo(neighborhood);
        assertThat(addressRequest.city()).isEqualTo(city);
        assertThat(addressRequest.state()).isEqualTo(state);
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Arrange
        String zipcode = "12345-678";
        String street = "Main Street";
        Integer number = 123;
        String complement = "Apt 4B";
        String neighborhood = "Downtown";
        String city = "New York";
        String state = "NY";
        
        // Act
        AddressRequestDTO addressRequest1 = new AddressRequestDTO(
                zipcode, street, number, complement, neighborhood, city, state);
        AddressRequestDTO addressRequest2 = new AddressRequestDTO(
                zipcode, street, number, complement, neighborhood, city, state);
        AddressRequestDTO differentAddress = new AddressRequestDTO(
                zipcode, "Different Street", number, complement, neighborhood, city, state);
        
        // Assert
        assertThat(addressRequest1).isEqualTo(addressRequest2);
        assertThat(addressRequest1.hashCode()).isEqualTo(addressRequest2.hashCode());
        
        assertThat(addressRequest1).isNotEqualTo(differentAddress);
        assertThat(addressRequest1.hashCode()).isNotEqualTo(differentAddress.hashCode());
    }
    
    @Test
    void testToString() {
        // Arrange
        String zipcode = "12345-678";
        String street = "Main Street";
        Integer number = 123;
        String complement = "Apt 4B";
        String neighborhood = "Downtown";
        String city = "New York";
        String state = "NY";
        
        // Act
        AddressRequestDTO addressRequest = new AddressRequestDTO(
                zipcode, street, number, complement, neighborhood, city, state);
        
        // Assert
        String toString = addressRequest.toString();
        assertThat(toString).contains("AddressRequestDTO");
        assertThat(toString).contains(zipcode);
        assertThat(toString).contains(street);
        assertThat(toString).contains(number.toString());
        assertThat(toString).contains(complement);
        assertThat(toString).contains(neighborhood);
        assertThat(toString).contains(city);
        assertThat(toString).contains(state);
    }
    
    @Test
    void testWithNullComplement() {
        // Arrange
        String zipcode = "12345-678";
        String street = "Main Street";
        Integer number = 123;
        String complement = null; // Null complement
        String neighborhood = "Downtown";
        String city = "New York";
        String state = "NY";
        
        // Act
        AddressRequestDTO addressRequest = new AddressRequestDTO(
                zipcode, street, number, complement, neighborhood, city, state);
        
        // Assert
        assertThat(addressRequest.zipcode()).isEqualTo(zipcode);
        assertThat(addressRequest.street()).isEqualTo(street);
        assertThat(addressRequest.number()).isEqualTo(number);
        assertThat(addressRequest.complement()).isNull();
        assertThat(addressRequest.neighborhood()).isEqualTo(neighborhood);
        assertThat(addressRequest.city()).isEqualTo(city);
        assertThat(addressRequest.state()).isEqualTo(state);
    }
    
    @Test
    void testWithDifferentStateFormats() {
        // Arrange
        String zipcode = "12345-678";
        String street = "Main Street";
        Integer number = 123;
        String complement = "Apt 4B";
        String neighborhood = "Downtown";
        String city = "New York";
        
        // Act - with lowercase state
        AddressRequestDTO addressRequest1 = new AddressRequestDTO(
                zipcode, street, number, complement, neighborhood, city, "ny");
        
        // Act - with uppercase state
        AddressRequestDTO addressRequest2 = new AddressRequestDTO(
                zipcode, street, number, complement, neighborhood, city, "NY");
        
        // Assert
        assertThat(addressRequest1).isNotEqualTo(addressRequest2);
        assertThat(addressRequest1.state()).isEqualTo("ny");
        assertThat(addressRequest2.state()).isEqualTo("NY");
    }
}