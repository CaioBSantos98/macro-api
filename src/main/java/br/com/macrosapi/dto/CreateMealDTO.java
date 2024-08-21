package br.com.macrosapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateMealDTO(
        @NotBlank
        String name,

        @NotNull
        @NotEmpty
        @Valid
        List<FoodItemListDTO> foodList
) {
}
