package br.com.macrosapi.dto.meal;

import br.com.macrosapi.dto.food.FoodItemListDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateMealDTO(
        @NotBlank
        String name,

        @NotBlank
        String date,

        @NotNull
        @NotEmpty
        @Valid
        List<FoodItemListDTO> foodList
) {
}
