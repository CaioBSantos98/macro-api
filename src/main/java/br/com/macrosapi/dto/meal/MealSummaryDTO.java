package br.com.macrosapi.dto.meal;

import java.time.LocalDate;

public record MealSummaryDTO(
        String name,
        LocalDate date,
        Double totalCalories,
        Double totalCarbohydrates,
        Double totalProtein,
        Double totalFat
) {
}
