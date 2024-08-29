package br.com.macrosapi.services;

import br.com.macrosapi.dto.meal.*;
import br.com.macrosapi.dto.food.FoodDetailsDTO;
import br.com.macrosapi.dto.food.FoodItemListDTO;
import br.com.macrosapi.model.food.Food;
import br.com.macrosapi.model.meal.Meal;
import br.com.macrosapi.model.mealfood.MealFood;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.repositories.FoodRepository;
import br.com.macrosapi.repositories.MealFoodRepository;
import br.com.macrosapi.repositories.MealRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void delete(UUID id, HttpServletRequest request) throws IllegalAccessException {
        Meal meal = mealRepository.getReferenceById(id);
        User user = userService.getUserByHttpRequest(request);

        if (meal.getUser().getId() != user.getId()) {
            throw new IllegalAccessException("You can only exclude your own meals");
        }

        mealRepository.deleteById(id);
    }

    public List<MealSummaryDTO> detailMealsByDate(LocalDate date, HttpServletRequest request) {
        User user = userService.getUserByHttpRequest(request);
        List<Meal> meals = mealRepository.findAllByUserAndDate(user, date);
        List<MealSummaryDTO> mealSummaryDTOList = new ArrayList<>();

        if (meals.isEmpty()) {
            throw new EntityNotFoundException("No meals found on this date");
        }

        meals.forEach(m -> {
            var mealSummaryDto = getMealSummary(m);
            mealSummaryDTOList.add(mealSummaryDto);
        });

        return mealSummaryDTOList;
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

        return new MealSummaryDTO(meal.getId(), meal.getName(), meal.getDate(), calories, carbohydrates, protein, fat);
    }

    public MealDetailsDTO addFood(AddFoodDTO dto, HttpServletRequest request) {
        User user = userService.getUserByHttpRequest(request);
        Meal meal = mealRepository.findByUserAndId(user, dto.mealId());

        // Para cada alimento que veio no DTO
        dto.foodList().forEach(foodItemDto -> {
            // Verifica se esse alimento já está cadastrado na refeição
            Boolean existsFoodOnMeal = existsThisFoodOnMeal(meal.getMealFoods(), foodItemDto.id());
            if (existsFoodOnMeal) {
                // Caso alimento já esteja cadastrado, apenas adicionar a quantidade
                MealFood mealFood = mealFoodRepository.findByMealIdAndFoodId(meal.getId(), foodItemDto.id());
                mealFood.addQuantity(foodItemDto.quantity());
            } else {
                // Caso alimento nao esteja cadastrado, adicionar o alimento na refeição
                Food food = foodRepository.getReferenceById(foodItemDto.id());
                meal.getMealFoods().add(new MealFood(foodItemDto.quantity(), meal, food));
            }
        });

        return new MealDetailsDTO(meal);
    }

    private Boolean existsThisFoodOnMeal(List<MealFood> mealFoods, UUID id) {
        for (MealFood mf : mealFoods) {
            if (mf.getFood().getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public MealDetailsDTO removeFood(UUID mealId, UUID foodId, HttpServletRequest request) {
        User user = userService.getUserByHttpRequest(request);
        Meal meal = mealRepository.findByUserAndId(user, mealId);

        Boolean existsFoodOnMeal = existsThisFoodOnMeal(meal.getMealFoods(), foodId);
        if (existsFoodOnMeal) {
            MealFood mealFood = mealFoodRepository.findByMealIdAndFoodId(meal.getId(), foodId);
            meal.getMealFoods().remove(mealFood);
            mealFoodRepository.delete(mealFood);
        } else {
            throw new EntityNotFoundException("Food not found on this meal.");
        }

        return new MealDetailsDTO(meal);
    }
}
