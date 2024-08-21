package br.com.macrosapi.dto.user;

import br.com.macrosapi.model.user.User;

import java.time.LocalDate;
import java.util.UUID;

public record UserDetailsDTO(
        UUID id,
        String name,
        String email,
        LocalDate birthDate
) {
    public UserDetailsDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getBirthDate());
    }
}
