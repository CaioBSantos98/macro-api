package br.com.macrosapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public record RegisterUserDTO(
        @NotBlank
        String name,

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birth date must be in the format YYYY-MM-DD")
        String birthDate
) {
        public LocalDate getBirthDateAsLocalDate() {
                try {
                        return LocalDate.parse(birthDate);
                } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Invalid birth date format");
                }
        }
}
