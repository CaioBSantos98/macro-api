package br.com.macrosapi.model.meal;

import br.com.macrosapi.model.mealfood.MealFood;
import br.com.macrosapi.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "meals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Meal {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
    private List<MealFood> mealFoods;

    public Meal(String name, LocalDate date, User user) {
        this.name = name;
        this.date = date;
        this.user = user;
    }
}
