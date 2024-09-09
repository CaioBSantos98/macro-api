package br.com.macrosapi.dto.meal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record UpdateMealDTO(
        @NotNull
        UUID mealId,

        @NotNull
        UUID foodId,

        @NotNull
        @Positive
        Double newQuantity
) {
}
