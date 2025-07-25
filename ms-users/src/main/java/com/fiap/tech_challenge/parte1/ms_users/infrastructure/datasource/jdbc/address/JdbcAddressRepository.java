package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.address;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.Address;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public List<Address> findAllByUserIds(Set<UUID> userIdSet) {
        if (userIdSet == null || userIdSet.isEmpty()) {
            return List.of();
        }

        // Generate placeholders like :id0, :id1, ...
        List<String> placeholders = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        int index = 0;
        for (UUID id : userIdSet) {
            String key = "id" + index++;
            placeholders.add(":" + key);
            params.put(key, id);
        }

        String inClause = String.join(", ", placeholders);

        String sql = """
                    SELECT
                        id, zipcode, street, number, complement, neighborhood, city, state, user_id
                    FROM
                        address
                    WHERE
                        user_id IN (%s)
                """.formatted(inClause);

        JdbcClient.StatementSpec spec = jdbcClient.sql(sql);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            spec = spec.param(entry.getKey(), entry.getValue());
        }

        return spec
                .query(Address.class)
                .list();
    }
}
