package br.com.macrosapi.controller;

import br.com.macrosapi.dto.LoginDTO;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.services.CookieService;
import br.com.macrosapi.services.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CookieService cookieService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO dto, HttpServletResponse response) {
        var token = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var authentication = manager.authenticate(token);
        var tokenJWT = tokenService.create((User) authentication.getPrincipal());
        var cookie = cookieService.createJWTCookie(tokenJWT);
        response.addCookie(cookie);

        return ResponseEntity.ok("Bem vindo " + ((User) authentication.getPrincipal()).getName());
    }
}
