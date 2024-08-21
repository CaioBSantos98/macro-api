package br.com.macrosapi.services;

import br.com.macrosapi.dto.CreateMealDTO;
import br.com.macrosapi.dto.FoodDetailsDTO;
import br.com.macrosapi.dto.FoodItemListDTO;
import br.com.macrosapi.dto.MealDetailsDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public MealDetailsDTO create(CreateMealDTO dto, HttpServletRequest request) {
        User user = userService.getUserByHttpRequest(request);
        Meal meal = new Meal(dto.name(), user);
        mealRepository.save(meal);

        List<FoodDetailsDTO> foodDTOList = new ArrayList<>();

        for (FoodItemListDTO foodItem : dto.foodList()) {
            if (!foodRepository.existsById(foodItem.id())) {
                throw new RuntimeException("Invalid food id");
            }
            Food food = foodRepository.getReferenceById(foodItem.id());
            MealFood mealFood = new MealFood(foodItem.quantity(), meal, food);
            mealFoodRepository.save(mealFood);
            foodDTOList.add(new FoodDetailsDTO(food));
        }

        return new MealDetailsDTO(meal.getId(), meal.getName(), meal.getDate(), foodDTOList);
    }

    public MealDetailsDTO detail(UUID id) {
        Meal meal = mealRepository.getReferenceById(id);
        List<FoodDetailsDTO> foodList = new ArrayList<>();
        meal.getMealFoods().forEach(mf -> foodList.add(new FoodDetailsDTO(mf.getFood())));
        return new MealDetailsDTO(meal.getId(), meal.getName(), meal.getDate(), foodList);
    }
}
