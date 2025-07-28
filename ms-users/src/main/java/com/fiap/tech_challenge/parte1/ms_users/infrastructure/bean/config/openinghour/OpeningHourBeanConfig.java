package com.fiap.tech_challenge.parte1.ms_users.infrastructure.bean.config.openinghour;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.openinghour.OpeningHourGateway;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.openinghour.OpeningHourGatewayImpl;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour.JdbcOpeningHourDataSource;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.openinghour.JdbcOpeningHourRepository;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.OpeningHourMapper;
import org.springframework.context.annotation.Bean;

public class OpeningHourBeanConfig {

    @Bean
    OpeningHourDataSource registerOpeningHourDataSource(JdbcOpeningHourRepository jdbcOpeningHourRepository, OpeningHourMapper openingHourMapper) {
        return new JdbcOpeningHourDataSource(jdbcOpeningHourRepository, openingHourMapper);
    }

    @Bean
    public OpeningHourGateway registerOpeningHourGateway(OpeningHourDataSource openingHourDataSource) {
        return new OpeningHourGatewayImpl(openingHourDataSource);
    }

//    @Bean
//    public OpeningHourControllerInputPort registerOpeningHourControllerInputPort(RegisterUserUseCase registerStudentUseCase, UpdateUserUseCase registerUpdateUserUseCase, FindListUserUseCase registerFindListUserUseCase, FindByIdUserUseCase registerFindByIdUserUserCase, DeactivateUserUseCase registerDeactivateUserUseCase, ReactivateUserUseCase registerReactivateUserUseCase, ChangePasswordUserUseCase registerChangePasswordUserUseCase, AuthenticateUserUseCase registerAuthenticateUserUseCase, IUserMapper iUserMapper) {
//        return new OpeningHourControllerInputPortImpl(registerStudentUseCase, registerUpdateUserUseCase, registerFindListUserUseCase, registerFindByIdUserUserCase, registerDeactivateUserUseCase, registerReactivateUserUseCase, registerChangePasswordUserUseCase, registerAuthenticateUserUseCase, iUserMapper);
//    }

    //UseCases
}
