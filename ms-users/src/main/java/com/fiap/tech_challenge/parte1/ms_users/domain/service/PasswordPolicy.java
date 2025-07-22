package com.fiap.tech_challenge.parte1.ms_users.domain.service;

import com.fiap.tech_challenge.parte1.ms_users.domain.exception.InvalidPasswordException;

public class PasswordPolicy {

    public void validate(boolean oldPasswordMatches, boolean isSameAsOld) {
        if (!oldPasswordMatches) {
            throw new InvalidPasswordException("Senha atual não confere");
        }
        if (isSameAsOld) {
            throw new InvalidPasswordException("Nova senha deve ser diferente da senha antiga");
        }
    }

}
