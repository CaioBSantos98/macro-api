package br.com.macrosapi.dto.meal;

import br.com.macrosapi.dto.food.FoodDetailsDTO;

public record MealFoodDTO(
        Double quantity,
        FoodDetailsDTO foodDetails
) {
}
