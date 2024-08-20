package br.com.macrosapi.controller;

import br.com.macrosapi.dto.FoodDetailsDTO;
import br.com.macrosapi.dto.RegisterFoodDTO;
import br.com.macrosapi.model.food.Food;
import br.com.macrosapi.services.FoodService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/food")
public class FoodController {

    @Autowired
    private FoodService service;

    @PostMapping
    @Transactional
    public ResponseEntity<FoodDetailsDTO> register(@RequestBody @Valid RegisterFoodDTO dto, HttpServletRequest request) {
        Food food = service.register(dto, request);

        return ResponseEntity.ok(new FoodDetailsDTO(food));
    }
}
