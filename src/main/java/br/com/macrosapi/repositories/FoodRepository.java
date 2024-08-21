package br.com.macrosapi.repositories;

import br.com.macrosapi.model.food.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {

    @Query(value = "SELECT * FROM foods f WHERE unaccent(f.name) ILIKE unaccent(:foodname)", nativeQuery = true)
    Page<Food> findAllByFoodName(@Param("foodname") String foodname, Pageable pageable);
}
