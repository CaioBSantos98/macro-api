package br.com.macrosapi.dto.food;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegisterFoodDTO(
        @NotBlank
        String name,
        String brand,

        @NotNull
        @Positive
        Double serving,

        @NotNull
        @Positive
        Double carbohydrate,

        @NotNull
        @Positive
        Double protein,

        @NotNull
        @Positive
        Double fat
) {
        public Double calculateCalories() {
                return (carbohydrate * 4) + (protein * 4) + (fat * 9);
        }
}
