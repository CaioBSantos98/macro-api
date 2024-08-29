package br.com.macrosapi.dto.meal;

import br.com.macrosapi.dto.food.FoodItemListDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AddFoodDTO(
        @NotNull
        UUID mealId,

        @NotNull
        @NotEmpty
        @Valid
        List<FoodItemListDTO> foodList
) {
}
