package br.com.macrosapi.controller;

import br.com.macrosapi.dto.CreateMealDTO;
import br.com.macrosapi.dto.MealDetailsDTO;
import br.com.macrosapi.services.MealService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/meal")
public class MealController {

    @Autowired
    private MealService service;

    @PostMapping
    @Transactional
    public ResponseEntity<MealDetailsDTO> create(@RequestBody @Valid CreateMealDTO dto, HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        MealDetailsDTO mealDetailsDTO = service.create(dto, request);
        URI uri = uriBuilder.path("/api/meal/{id}").buildAndExpand(mealDetailsDTO.id()).toUri();
        return ResponseEntity.created(uri).body(mealDetailsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealDetailsDTO> detail(@PathVariable UUID id) {
        try {
            MealDetailsDTO mealDto = service.detail(id);
            return ResponseEntity.ok(mealDto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
