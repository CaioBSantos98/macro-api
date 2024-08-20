package br.com.macrosapi.repositories;

import br.com.macrosapi.model.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {
}
