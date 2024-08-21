package br.com.macrosapi.controller;

import br.com.macrosapi.dto.FoodDetailsDTO;
import br.com.macrosapi.dto.RegisterFoodDTO;
import br.com.macrosapi.model.food.Food;
import br.com.macrosapi.services.FoodService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/food")
public class FoodController {

    @Autowired
    private FoodService service;

    @PostMapping
    @Transactional
    public ResponseEntity<FoodDetailsDTO> register(@RequestBody @Valid RegisterFoodDTO dto, HttpServletRequest request, UriComponentsBuilder uriBuilder) {
        Food food = service.register(dto, request);
        URI uri = uriBuilder.path("/api/food/{id}").buildAndExpand(food.getId()).toUri();
        FoodDetailsDTO foodDto = new FoodDetailsDTO(food);
        return ResponseEntity.created(uri).body(foodDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodDetailsDTO> detail(@PathVariable UUID id) {
        try {
            FoodDetailsDTO foodDto = service.detail(id);
            return ResponseEntity.ok(foodDto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/{foodname}")
    public ResponseEntity<Page<FoodDetailsDTO>> search(@PathVariable String foodname, @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        var search = service.searchFood(foodname, pageable);

        return ResponseEntity.ok(search);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable UUID id, HttpServletRequest request) {
        try {
            service.delete(id, request);
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
