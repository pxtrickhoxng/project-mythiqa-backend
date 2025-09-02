package com.mythiqa.mythiqabackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException(String username) {
        super("Username '" + username + "' is already taken");
    }
}
