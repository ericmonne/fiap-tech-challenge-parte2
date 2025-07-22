package com.fiap.tech_challenge.parte1.ms_users.infrastructure.validators;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.UsersRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.UserValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.DuplicatedAddressException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validator that checks for duplicate addresses in a user's request.
 * <p>
 * Implements the {@link UserValidator} interface and ensures that all addresses
 * in a user request are unique by comparing all relevant address fields.
 */
@Component
public class DuplicatedAddressValidator implements UserValidator {

    @Override
    public void validate(User user) {
        List<Address> addresses = user.getAddress();
        validateAddress(addresses);
    }

    /**
     * Checks a list of addresses for duplicates.
     *
     * @param addresses the list of addresses to validate.
     * @throws DuplicatedAddressException if any duplicate addresses are detected.
     */
    public void validateAddress(List<Address> addresses) {
        Set<String> uniqueAddressKeys = new HashSet<>();
        for (Address address : addresses) {
            String key = generateAddressKey(address);

            if (!uniqueAddressKeys.add(key)) {
                throw new DuplicatedAddressException(
                        "Duplicate address detected: one or more addresses contain exactly the same data."
                );
            }
        }
    }

    /**
     * Generates a unique key for an address by concatenating its normalized fields.
     *
     * @param address the address object to generate the key for.
     * @return a string key representing the address.
     */
    public String generateAddressKey(Address address) {
        return String.join("|",
                normalize(address.getZipcode()),
                normalize(address.getStreet()),
                normalize(address.getNumber() != null ? address.getNumber().toString() : null),
                normalize(address.getComplement()),
                normalize(address.getNeighborhood()),
                normalize(address.getCity()),
                normalize(address.getState())
        );
    }

    /**
     * Normalizes a string value by trimming whitespace and converting to lowercase.
     * Returns an empty string if the value is null.
     *
     * @param value the string to normalize.
     * @return the normalized string or an empty string if null.
     */
    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase();
    }
}
