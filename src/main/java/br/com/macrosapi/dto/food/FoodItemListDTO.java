package br.com.macrosapi.dto.food;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record FoodItemListDTO(
        @NotNull
        UUID id,

        @NotNull
        @Positive(message = "Quantity must be greater than zero.")
        Double quantity
) {
}
