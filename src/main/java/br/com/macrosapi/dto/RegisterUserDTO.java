package br.com.macrosapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterUserDTO(
        @NotBlank
        String name,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotNull
        LocalDate birthDate
) {
}
