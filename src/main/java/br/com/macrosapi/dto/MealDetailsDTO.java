package br.com.macrosapi.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record MealDetailsDTO(
        UUID id,
        String name,
        LocalDate date,
        List<FoodDetailsDTO> foodList
) {
}
