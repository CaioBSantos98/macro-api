package br.com.macrosapi.controller;

import br.com.macrosapi.dto.RegisterUserDTO;
import br.com.macrosapi.dto.UserDetailsDTO;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDetailsDTO> create(@RequestBody @Valid RegisterUserDTO dto, UriComponentsBuilder uriBuilder) {
        User user = service.create(dto);
        URI uri = uriBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri();
        UserDetailsDTO userDto = new UserDetailsDTO(user);
        return ResponseEntity.created(uri).body(userDto);
    }

    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> detail(@PathVariable UUID id) {
        UserDetailsDTO userDto = service.detail(id);
        return ResponseEntity.ok(userDto);
    }
}
