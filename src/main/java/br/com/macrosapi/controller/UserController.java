package br.com.macrosapi.controller;

import br.com.macrosapi.dto.user.RegisterUserDTO;
import br.com.macrosapi.dto.user.UserDetailsDTO;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.services.UserService;
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
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailsDTO> create(@RequestBody @Valid RegisterUserDTO dto, UriComponentsBuilder uriBuilder) {
        User user = service.create(dto);
        URI uri = uriBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri();
        UserDetailsDTO userDto = new UserDetailsDTO(user);
        return ResponseEntity.created(uri).body(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> detail(@PathVariable UUID id) {
        try {
            UserDetailsDTO userDto = service.detail(id);
            return ResponseEntity.ok(userDto);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<UserDetailsDTO> detailByHttpRequest(HttpServletRequest request) {
        try {
            User user = service.getUserByHttpRequest(request);
            return ResponseEntity.ok(new UserDetailsDTO(user));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable UUID id, HttpServletRequest request) {
        try {
            service.delete(id, request);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
