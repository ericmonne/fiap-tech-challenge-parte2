package com.fiap.tech_challenge.parte1.ms_users.services;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.ChangePasswordRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.UserNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import com.fiap.tech_challenge.parte1.ms_users.repositories.UserRepository;
import com.fiap.tech_challenge.parte1.ms_users.services.validation.PasswordValidationService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class responsible for managing user operations such as
 * creation, update, retrieval, password changes, and activation status.
 */
@Service
public class UsersService {

    private final UserRepository userRepository;
    private final PasswordValidationService passwordValidationService;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UserRepository userRepository, PasswordValidationService passwordValidationService,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordValidationService = passwordValidationService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Deactivates a user by ID.
     *
     * @param id the user ID
     * @throws UserNotFoundException if the user is not found
     */
    public void deactivateUser(UUID id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("Usuário com id %s não encontrado.", id)));
        userRepository.deactivate(user.getId());
    }

    /**
     * Reactivates a previously deactivated user.
     *
     * @param id the user ID
     * @throws UserNotFoundException if the user is not found
     */
    public void reactivateUser(UUID id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("Usuário com id %s não encontrado.", id)));
        userRepository.reactivate(user.getId());
    }

    /**
     * Changes the password of a user after validating the old password.
     *
     * @param id  the user ID
     * @param dto the change password request DTO
     * @throws UserNotFoundException if the user is not found
     */
    public void changePassword(UUID id, @Valid ChangePasswordRequestDTO dto) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("Usuário com id %s não encontrado.", id)));

        boolean oldPasswordMatches = passwordEncoder.matches(dto.oldPassword(), user.getPassword());
        boolean isSameAsOld = dto.oldPassword().equals(dto.newPassword());

        passwordValidationService.validate(oldPasswordMatches, isSameAsOld);
        String newPasswordEncoded = passwordEncoder.encode(dto.newPassword());
        userRepository.changePassword(id, newPasswordEncoded);
    }

}
