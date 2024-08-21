package br.com.macrosapi.dto.meal;

import br.com.macrosapi.dto.food.FoodDetailsDTO;

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
