package br.com.macrosapi.services;

import br.com.macrosapi.dto.FoodDetailsDTO;
import br.com.macrosapi.dto.RegisterFoodDTO;
import br.com.macrosapi.model.food.Food;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.repositories.FoodRepository;
import br.com.macrosapi.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Food register(RegisterFoodDTO dto, HttpServletRequest request) {
        var tokenJWT = tokenService.recoverTokenFromCookies(request);
        var subject = tokenService.getSubject(tokenJWT);
        var user = userRepository.findUserByEmailAndActiveTrue(subject);

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
        String token = tokenService.recoverTokenFromCookies(request);
        String email = tokenService.getSubject(token);

        if (!email.equals(food.getUser().getEmail())) {
            throw new IllegalAccessException("You can only exclude your own foods");
        }

        foodRepository.deleteById(id);
    }
}
