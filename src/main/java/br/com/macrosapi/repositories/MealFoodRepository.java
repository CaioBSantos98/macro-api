package br.com.macrosapi.repositories;

import br.com.macrosapi.model.mealfood.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MealFoodRepository extends JpaRepository<MealFood, UUID> {
    MealFood findByMealId(UUID id);
}
