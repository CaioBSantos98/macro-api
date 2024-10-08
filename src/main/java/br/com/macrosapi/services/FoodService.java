package br.com.macrosapi.services;

import br.com.macrosapi.dto.food.FoodDetailsDTO;
import br.com.macrosapi.dto.food.RegisterFoodDTO;
import br.com.macrosapi.model.food.Food;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.repositories.FoodRepository;
import br.com.macrosapi.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    public Food register(RegisterFoodDTO dto, HttpServletRequest request) {
        User user = userService.getUserByHttpRequest(request);

        Food food = new Food(dto, user);
        foodRepository.save(food);
        return food;
    }

    public FoodDetailsDTO detail(UUID id) {
        Food food = foodRepository.getReferenceById(id);

        return new FoodDetailsDTO(food);
    }

    public void delete(UUID id, HttpServletRequest request) throws IllegalAccessException {
        Food food = foodRepository.getReferenceById(id);
        User user = userService.getUserByHttpRequest(request);

        if (food.getUser().getId() != user.getId()) {
            throw new IllegalAccessException("You can only exclude your own foods");
        }

        foodRepository.deleteById(id);
    }

    public Page<FoodDetailsDTO> searchFood(String foodname, Pageable pageable) {
        return foodRepository.findAllByFoodName("%"+foodname+"%", pageable).map(FoodDetailsDTO::new);
    }
}
