package br.com.macrosapi.controller;

import br.com.macrosapi.dto.CreateMealDTO;
import br.com.macrosapi.services.MealService;
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
@RequestMapping("api/meal")
public class MealController {

    @Autowired
    private MealService service;

    @PostMapping
    @Transactional
    public ResponseEntity<String> create(@RequestBody @Valid CreateMealDTO dto, HttpServletRequest request) {
        service.create(dto, request);
        return ResponseEntity.ok().build();
    }
}
