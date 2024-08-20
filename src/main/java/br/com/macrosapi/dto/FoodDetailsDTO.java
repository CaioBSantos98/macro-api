package br.com.macrosapi.dto;

import br.com.macrosapi.model.food.Food;

import java.util.UUID;

public record FoodDetailsDTO(
        UUID id,
        String name,
        String brand,
        Double serving,
        Double calories,
        Double carbohydrate,
        Double protein,
        Double fat,
        UUID userId
) {
    public FoodDetailsDTO(Food food) {
        this(food.getId(), food.getName(), food.getBrand(), food.getServing(), food.getCalories(), food.getCarbohydrate(), food.getProtein(), food.getFat(), food.getUser().getId());
    }
}
