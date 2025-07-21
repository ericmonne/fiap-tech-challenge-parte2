package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JdbcAddressRepository {

    private final JdbcClient jdbcClient;

    public JdbcAddressRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void save(List<JdbcAddressEntity> addresses, UUID generatedUserId) {
        for (JdbcAddressEntity address : addresses) {
            jdbcClient.sql("""
                                INSERT INTO address (
                                    user_id, zipcode, street, number, complement, neighborhood, city, state
                                ) VALUES (
                                    :user_id, :zipcode, :street, :number, :complement, :neighborhood, :city, :state
                                );
                            """)
                    .param("user_id", generatedUserId)
                    .param("zipcode", address.getZipcode())
                    .param("street", address.getStreet())
                    .param("number", address.getNumber())
                    .param("complement", address.getComplement())
                    .param("neighborhood", address.getNeighborhood())
                    .param("city", address.getCity())
                    .param("state", address.getState())
                    .update();
        }
    }
}
