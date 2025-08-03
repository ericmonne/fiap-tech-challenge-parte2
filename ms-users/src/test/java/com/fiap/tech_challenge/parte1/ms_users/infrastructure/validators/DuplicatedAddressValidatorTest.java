package com.fiap.tech_challenge.parte1.ms_users.infrastructure.validators;

import com.fiap.tech_challenge.parte1.ms_users.domain.exception.DuplicatedAddressException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DuplicatedAddressValidatorTest {

    private DuplicatedAddressValidator validator;

    @BeforeEach
    void setUp() {
        validator = new DuplicatedAddressValidator();
    }

    @Test
    void validate_shouldPass_whenAddressesAreUnique() {
        Address address1 = createAddress("12345", "Street A", 100, "Apt 1", "Neighborhood", "City", "State");
        Address address2 = createAddress("54321", "Street B", 200, "Apt 2", "Neighborhood", "City", "State");

        User user = mock(User.class);
        when(user.getAddress()).thenReturn(List.of(address1, address2));

        assertDoesNotThrow(() -> validator.validate(user));
    }

    @Test
    void validate_shouldThrowException_whenDuplicateAddressesFound() {
        Address address1 = createAddress("12345", "Street A", 100, "Apt 1", "Neighborhood", "City", "State");
        Address address2 = createAddress("12345", "Street A", 100, "Apt 1", "Neighborhood", "City", "State");

        User user = mock(User.class);
        when(user.getAddress()).thenReturn(List.of(address1, address2));

        DuplicatedAddressException ex = assertThrows(DuplicatedAddressException.class, () -> validator.validate(user));
        assertEquals("Duplicate address detected: one or more addresses contain exactly the same data.", ex.getMessage());
    }

    @Test
    void generateAddressKey_shouldGenerateNormalizedKey() {
        Address address = createAddress(" 12345 ", " Street A ", 100, " Apt 1 ", " Neighborhood ", " City ", " State ");
        String key = validator.generateAddressKey(address);

        assertEquals("12345|street a|100|apt 1|neighborhood|city|state", key);
    }

    @Test
    void normalize_shouldReturnEmptyString_whenNull() throws Exception {
        var method = DuplicatedAddressValidator.class.getDeclaredMethod("normalize", String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(validator, (Object) null);
        assertEquals("", result);
    }

    @Test
    void normalize_shouldTrimAndLowercase() throws Exception {
        var method = DuplicatedAddressValidator.class.getDeclaredMethod("normalize", String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(validator, "  Test String  ");
        assertEquals("test string", result);
    }

    private Address createAddress(String zipcode, String street, Integer number, String complement, String neighborhood, String city, String state) {
        Address address = new Address();
        address.setZipcode(zipcode);
        address.setStreet(street);
        address.setNumber(number);
        address.setComplement(complement);
        address.setNeighborhood(neighborhood);
        address.setCity(city);
        address.setState(state);
        return address;
    }
}
