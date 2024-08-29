package br.com.macrosapi.dto.meal;

import br.com.macrosapi.dto.food.FoodDetailsDTO;
import br.com.macrosapi.model.mealfood.MealFood;

public record MealFoodDTO(
        Double quantity,
        FoodDetailsDTO foodDetails
) {
    public MealFoodDTO(MealFood mealFood) {
        this(mealFood.getFoodQuantity(), new FoodDetailsDTO(mealFood.getFood()));
    }
}
