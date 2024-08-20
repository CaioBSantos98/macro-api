package br.com.macrosapi.services;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    public Cookie createJWTCookie(String tokenJWT) {
        Cookie cookie = new Cookie("JWT", tokenJWT);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);

        return cookie;
    }
}
