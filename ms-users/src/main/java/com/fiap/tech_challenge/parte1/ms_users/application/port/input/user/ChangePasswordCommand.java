package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user;

public record ChangePasswordCommand(String oldPassword, String newPassword) {
}
