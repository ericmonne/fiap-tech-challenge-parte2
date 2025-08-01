package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@Profile("test")
public class TestDatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db/migration/V1__init.sql")
                .addScript("classpath:db/migration/V2__rename_zipcode.sql")
                .addScript("classpath:db/migration/V3__update_ids_to_uuid.sql")
                .addScript("classpath:db/migration/V4__change_user_address_relation_to_one_to_many.sql")
                .addScript("classpath:db/migration/V5__add_on_delete_cascade_to_address_user_id_fk.sql")
                .addScript("classpath:db/migration/V6__rename_active.sql")
                .addScript("classpath:db/migration/V7__create_table_usertype.sql")
                .addScript("classpath:db/migration/V8__alter_table_users_create_column_user_type_id.sql")
                .addScript("classpath:db/migration/V9__create_table_restaurant.sql")
                .addScript("classpath:db/migration/V10__create_table_openinghour.sql")
                .addScript("classpath:db/migration/V11__alter_table_address.sql")
                .addScript("classpath:db/test/create_menu_item_table.sql")
                .build();
    }

    @Bean
    public JdbcClient jdbcClient(DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }
}
