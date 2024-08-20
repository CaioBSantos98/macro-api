package br.com.macrosapi.model.user;

import br.com.macrosapi.dto.RegisterUserDTO;
import br.com.macrosapi.model.food.Food;
import br.com.macrosapi.model.meal.Meal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String email;
    private String password;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private Boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Food> foods;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Meal> meals;

    public User(RegisterUserDTO dto, String encodedPassword) {
        this.name = dto.name();
        this.email = dto.email();
        this.password = encodedPassword;
        this.birthDate = dto.getBirthDateAsLocalDate();
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public void delete() {
        this.active = false;
    }
}
