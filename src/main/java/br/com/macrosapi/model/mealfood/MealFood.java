package br.com.macrosapi.model.mealfood;

import br.com.macrosapi.model.food.Food;
import br.com.macrosapi.model.meal.Meal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "meal_foods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class MealFood {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double foodQuantity;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    public MealFood(Double quantity, Meal meal, Food food) {
        this.foodQuantity = quantity;
        this.meal = meal;
        this.food = food;
    }
}
