package br.com.macrosapi.repositories;

import br.com.macrosapi.model.meal.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, UUID> {
}
