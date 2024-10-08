package br.com.macrosapi.model.food;

import br.com.macrosapi.dto.food.RegisterFoodDTO;
import br.com.macrosapi.model.mealfood.MealFood;
import br.com.macrosapi.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "foods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String brand;
    private Double serving;
    private Double calories;
    private Double carbohydrate;
    private Double protein;
    private Double fat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<MealFood> mealFoods;

    public Food(RegisterFoodDTO dto, User user) {
        this.name = dto.name();
        if (dto.brand() != null) this.brand = dto.brand();
        this.serving = dto.serving();
        this.calories = dto.calculateCalories();
        this.carbohydrate = dto.carbohydrate();
        this.protein = dto.protein();
        this.fat = dto.fat();
        this.user = user;
    }
}
