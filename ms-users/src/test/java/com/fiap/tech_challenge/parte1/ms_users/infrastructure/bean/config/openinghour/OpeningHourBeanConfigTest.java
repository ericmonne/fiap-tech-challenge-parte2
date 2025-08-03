package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.openinghour.OpeningHourGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour.JdbcOpeningHourDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour.JdbcOpeningHourRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.OpeningHourMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpeningHourBeanConfigTest {

    @Mock
    private JdbcOpeningHourRepository jdbcOpeningHourRepository;

    @Mock
    private OpeningHourMapper openingHourMapper;

    @Test
    void testRegisterOpeningHourDataSource() {
        OpeningHourBeanConfig config = new OpeningHourBeanConfig();

        OpeningHourDataSource dataSource = config.registerOpeningHourDataSource(jdbcOpeningHourRepository, openingHourMapper);

        assertNotNull(dataSource);
        assertTrue(dataSource instanceof JdbcOpeningHourDataSource);
    }

    @Test
    void testRegisterOpeningHourGateway() {
        OpeningHourBeanConfig config = new OpeningHourBeanConfig();
        OpeningHourDataSource dataSource = mock(OpeningHourDataSource.class);

        OpeningHourGateway gateway = config.registerOpeningHourGateway(dataSource);

        assertNotNull(gateway);
        assertTrue(gateway instanceof OpeningHourGatewayImpl);
    }

    @Test
    void testSpringContextLoading() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(OpeningHourBeanConfig.class);
        context.registerBean(JdbcOpeningHourRepository.class, () -> jdbcOpeningHourRepository);
        context.registerBean(OpeningHourMapper.class, () -> openingHourMapper);
        context.refresh();

        OpeningHourDataSource dataSource = context.getBean(OpeningHourDataSource.class);
        OpeningHourGateway gateway = context.getBean(OpeningHourGateway.class);

        assertNotNull(dataSource);
        assertNotNull(gateway);
        assertTrue(dataSource instanceof JdbcOpeningHourDataSource);
        assertTrue(gateway instanceof OpeningHourGatewayImpl);

        context.close();
    }
}