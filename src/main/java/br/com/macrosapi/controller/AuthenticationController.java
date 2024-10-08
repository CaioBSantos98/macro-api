package br.com.macrosapi.controller;

import br.com.macrosapi.dto.user.LoginDTO;
import br.com.macrosapi.dto.user.UserDetailsDTO;
import br.com.macrosapi.model.user.User;
import br.com.macrosapi.services.CookieService;
import br.com.macrosapi.services.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto, HttpServletResponse response) {
        var token = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        try {
            var authentication = manager.authenticate(token);
            var tokenJWT = tokenService.create((User) authentication.getPrincipal());
            var cookie = cookieService.createJWTCookie(tokenJWT);
            response.addCookie(cookie);

            var userDetails = new UserDetailsDTO((User) authentication.getPrincipal());

            return ResponseEntity.ok(userDetails);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
