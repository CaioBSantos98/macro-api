package br.com.macrosapi.services;

import br.com.macrosapi.dto.meal.CreateMealDTO;
import br.com.macrosapi.dto.food.FoodDetailsDTO;
import br.com.macrosapi.dto.food.FoodItemListDTO;
import br.com.macrosapi.dto.meal.MealDetailsDTO;
import br.com.macrosapi.dto.meal.MealFoodDTO;
import br.com.macrosapi.dto.meal.MealSummaryDTO;
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

        List<MealFoodDTO> foodDTOList = new ArrayList<>();

        for (FoodItemListDTO foodItem : dto.foodList()) {
            if (!foodRepository.existsById(foodItem.id())) {
                throw new RuntimeException("Invalid food id");
            }
            Food food = foodRepository.getReferenceById(foodItem.id());
            MealFood mealFood = new MealFood(foodItem.quantity(), meal, food);
            mealFoodRepository.save(mealFood);
            foodDTOList.add(new MealFoodDTO(mealFood.getFoodQuantity(), new FoodDetailsDTO(food)));
        }

        return new MealDetailsDTO(meal.getId(), meal.getName(), meal.getDate(), foodDTOList);
    }

    public MealDetailsDTO detail(UUID id) {
        Meal meal = mealRepository.getReferenceById(id);
        List<MealFoodDTO> foodList = new ArrayList<>();
        meal.getMealFoods().forEach(mf -> foodList.add(new MealFoodDTO(mf.getFoodQuantity(), new FoodDetailsDTO(mf.getFood()))));
        return new MealDetailsDTO(meal.getId(), meal.getName(), meal.getDate(), foodList);
    }

    public MealSummaryDTO summaryDetails(UUID id) {
        Meal meal = mealRepository.getReferenceById(id);
        return getMealSummary(meal);
    }

    private MealSummaryDTO getMealSummary(Meal meal) {
        Double calories = 0.0;
        Double carbohydrates = 0.0;
        Double protein = 0.0;
        Double fat = 0.0;

        for (MealFood mf : meal.getMealFoods()) {
            calories += mf.getFood().getCalories() * mf.getFoodQuantity();
            carbohydrates += mf.getFood().getCarbohydrate() * mf.getFoodQuantity();
            protein += mf.getFood().getProtein() * mf.getFoodQuantity();
            fat += mf.getFood().getFat() * mf.getFoodQuantity();
        }

        return new MealSummaryDTO(meal.getName(), meal.getDate(), calories, carbohydrates, protein, fat);
    }
}
