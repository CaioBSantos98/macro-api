package br.com.macrosapi.services;

import br.com.macrosapi.dto.CreateMealDTO;
import br.com.macrosapi.dto.FoodItemListDTO;
import br.com.macrosapi.model.food.Food;
import br.com.macrosapi.model.meal.Meal;
import br.com.macrosapi.model.mealfood.MealFood;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.repositories.FoodRepository;
import br.com.macrosapi.repositories.MealFoodRepository;
import br.com.macrosapi.repositories.MealRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private MealFoodRepository mealFoodRepository;

    @Autowired
    private UserService userService;

    public void create(CreateMealDTO dto, HttpServletRequest request) {
        User user = userService.getUserByHttpRequest(request);
        Meal meal = new Meal(dto.name(), user);
        mealRepository.save(meal);

        for (FoodItemListDTO foodItem : dto.foodList()) {
            if (!foodRepository.existsById(foodItem.id())) {
                throw new RuntimeException("Invalid food id");
            }
            Food food = foodRepository.getReferenceById(foodItem.id());
            MealFood mealFood = new MealFood(foodItem.quantity(), meal, food);
            mealFoodRepository.save(mealFood);
        }
    }
}
