package br.com.macrosapi.dto.meal;

import br.com.macrosapi.model.meal.Meal;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record MealDetailsDTO(
        UUID id,
        String name,
        LocalDate date,
        List<MealFoodDTO> foodList
) {
    public MealDetailsDTO(Meal meal) {
        this(meal.getId(), meal.getName(), meal.getDate(), meal.getMealFoods().stream().map(MealFoodDTO::new).toList());
    }
}
