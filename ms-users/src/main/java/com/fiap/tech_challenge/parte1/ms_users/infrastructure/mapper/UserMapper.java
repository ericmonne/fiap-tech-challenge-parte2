package com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.UpdateUserDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.UsersRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.UsersResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.IAddressMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.IUserMapper;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.user.JdbcUserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Mapper component responsible for converting {@link User} entities
 * to {@link UsersResponseDTO} data transfer objects.
 */
@Component
public class UserMapper implements IUserMapper {

    private final IAddressMapper iAddressMapper;

    public UserMapper(IAddressMapper iAddressMapper) {
        this.iAddressMapper = iAddressMapper;
    }


    /**
     * Converts a single {@link User} entity to a {@link UsersResponseDTO}.
     *
     * @param user the {@link User} entity to convert
     * @return the corresponding {@link UsersResponseDTO}
     */
    @Override
    public UsersResponseDTO toResponseDTO(User user) {
        return new UsersResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getRole().name(),
                iAddressMapper.toAddressRequestDTO(user.getAddresses()));
    }

    /**
     * Converts a list of {@link User} entities to a list of {@link UsersResponseDTO}s.
     *
     * @param users the list of {@link User} entities to convert
     * @return a list of corresponding {@link UsersResponseDTO}s
     */
    @Override
    public List<UsersResponseDTO> toResponseDTO(List<User> users) {
        return users.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public JdbcUserEntity toJdbcUserEntity(User user) {
        var jdbcUserEntity = new JdbcUserEntity();

        jdbcUserEntity.setId(user.getId());
        jdbcUserEntity.setName(user.getName());
        jdbcUserEntity.setEmail(user.getEmail());
        jdbcUserEntity.setLogin(user.getLogin());
        jdbcUserEntity.setActive(user.getActive());
        jdbcUserEntity.setRole(user.getRole().name());
        jdbcUserEntity.setPassword(user.getPassword());
        jdbcUserEntity.setDateLastChange(user.getDateLastChange());

        return jdbcUserEntity;
    }

    @Override
    public User toEntity(UsersRequestDTO dto) {
        User user = new User();
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setLogin(dto.login());
        user.setPassword(dto.password());
        user.setAddress(iAddressMapper.toEntity(dto.address()));
        return user;
    }

    @Override
    public User toEntity(UpdateUserDTO dto, UUID userId) {
        User user = new User();
        user.setId(userId);
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setLogin(dto.login());
        user.setAddress(iAddressMapper.toEntity(dto.address()));
        return user;
    }

}
