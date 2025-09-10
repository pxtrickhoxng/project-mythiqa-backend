package com.mythiqa.mythiqabackend.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

public class JwtUtils {
    private JwtUtils() {}

    public static String getUserIdFromJwt (Jwt jwt) {
        String requesterUserId = jwt.getClaim("sub");
        if (requesterUserId == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in JWT.");
        }
        return requesterUserId;
    }
}
