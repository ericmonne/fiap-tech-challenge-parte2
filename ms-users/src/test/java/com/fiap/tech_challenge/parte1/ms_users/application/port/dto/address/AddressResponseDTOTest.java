package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AddressResponseDTOTest {

    @Test
    void testAddressResponseDTO() {
        // Arrange
        UUID id = UUID.randomUUID();
        String zipcode = "12345-678";
        String street = "Main Street";
        Integer number = 123;
        String complement = "Apt 4B";
        String neighborhood = "Downtown";
        String city = "New York";
        String state = "NY";

        // Act
        AddressResponseDTO addressResponse = new AddressResponseDTO(
                id, zipcode, street, number, complement, neighborhood, city, state);

        // Assert
        assertThat(addressResponse.id()).isEqualTo(id);
        assertThat(addressResponse.zipcode()).isEqualTo(zipcode);
        assertThat(addressResponse.street()).isEqualTo(street);
        assertThat(addressResponse.number()).isEqualTo(number);
        assertThat(addressResponse.complement()).isEqualTo(complement);
        assertThat(addressResponse.neighborhood()).isEqualTo(neighborhood);
        assertThat(addressResponse.city()).isEqualTo(city);
        assertThat(addressResponse.state()).isEqualTo(state);
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        UUID id = UUID.randomUUID();
        String zipcode = "12345-678";
        String street = "Main Street";
        Integer number = 123;
        String complement = "Apt 4B";
        String neighborhood = "Downtown";
        String city = "New York";
        String state = "NY";

        // Act
        AddressResponseDTO addressResponse1 = new AddressResponseDTO(
                id, zipcode, street, number, complement, neighborhood, city, state);
        AddressResponseDTO addressResponse2 = new AddressResponseDTO(
                id, zipcode, street, number, complement, neighborhood, city, state);
        AddressResponseDTO differentAddress = new AddressResponseDTO(
                id, zipcode, "Different Street", number, complement, neighborhood, city, state);

        // Assert
        assertThat(addressResponse1).isEqualTo(addressResponse2);
        assertThat(addressResponse1).hasSameHashCodeAs(addressResponse2);
        assertThat(addressResponse1).isNotEqualTo(differentAddress);
        assertThat(addressResponse1.hashCode()).isNotEqualTo(differentAddress.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        UUID id = UUID.randomUUID();
        String zipcode = "12345-678";
        String street = "Main Street";
        Integer number = 123;
        String complement = "Apt 4B";
        String neighborhood = "Downtown";
        String city = "New York";
        String state = "NY";

        // Act
        AddressResponseDTO addressResponse = new AddressResponseDTO(
                id, zipcode, street, number, complement, neighborhood, city, state);

        // Assert
        String toString = addressResponse.toString();
        assertThat(toString).contains("AddressResponseDTO");
        assertThat(toString).contains(id.toString());
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
        UUID id = UUID.randomUUID();
        String zipcode = "12345-678";
        String street = "Main Street";
        Integer number = 123;
        String complement = null; // Null complement
        String neighborhood = "Downtown";
        String city = "New York";
        String state = "NY";

        // Act
        AddressResponseDTO addressResponse = new AddressResponseDTO(
                id, zipcode, street, number, complement, neighborhood, city, state);

        // Assert
        assertThat(addressResponse.id()).isEqualTo(id);
        assertThat(addressResponse.zipcode()).isEqualTo(zipcode);
        assertThat(addressResponse.street()).isEqualTo(street);
        assertThat(addressResponse.number()).isEqualTo(number);
        assertThat(addressResponse.complement()).isNull();
        assertThat(addressResponse.neighborhood()).isEqualTo(neighborhood);
        assertThat(addressResponse.city()).isEqualTo(city);
        assertThat(addressResponse.state()).isEqualTo(state);
    }
}