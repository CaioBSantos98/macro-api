package br.com.macrosapi.dto.meal;

import java.time.LocalDate;
import java.util.UUID;

public record MealSummaryDTO(
        UUID id,
        String name,
        LocalDate date,
        Double totalCalories,
        Double totalCarbohydrates,
        Double totalProtein,
        Double totalFat
) {
}
