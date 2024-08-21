package br.com.macrosapi.dto.food;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterFoodDTO(
        @NotBlank
        String name,
        String brand,

        @NotNull
        Double serving,

        @NotNull
        Double carbohydrate,

        @NotNull
        Double protein,

        @NotNull
        Double fat
) {
        public Double calculateCalories() {
                return (carbohydrate * 4) + (protein * 4) + (fat * 9);
        }
}
