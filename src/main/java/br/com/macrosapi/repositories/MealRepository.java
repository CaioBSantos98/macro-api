package br.com.macrosapi.repositories;

import br.com.macrosapi.model.meal.Meal;
import br.com.macrosapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, UUID> {
    List<Meal> findAllByUserAndDate(User user, LocalDate date);

    Meal findByUserAndId(User user, UUID id);
}
