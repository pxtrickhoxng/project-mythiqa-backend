package com.mythiqa.mythiqabackend.controller;

import com.mythiqa.mythiqabackend.dto.request.UpdateUserRequestDto;
import com.mythiqa.mythiqabackend.dto.response.*;
import com.mythiqa.mythiqabackend.model.User;
import com.mythiqa.mythiqabackend.projection.user.UserDisplayNameProjection;
import com.mythiqa.mythiqabackend.projection.user.UserProfileImgProjection;
import com.mythiqa.mythiqabackend.projection.user.UserProfileProjection;
import com.mythiqa.mythiqabackend.repository.BookRepository;
import com.mythiqa.mythiqabackend.repository.UserRepository;
import com.mythiqa.mythiqabackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, BookRepository bookRepository, UserService userService) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    @GetMapping("/{userId}/exists")
    public ResponseEntity<Void> checkUserExists(@PathVariable String userId) {
        if (!userService.checkUserExistsById(userId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/profile")
    public UserProfileDTO getUserByUsername(@PathVariable String username) {
        return userService.getUserProfileByUsername(username);
    }

    @GetMapping("/{username}/profile-img")
    public UserProfileImgDTO getUserProfileImg(@PathVariable String username) {
        return userService.getProfileImgByUsername(username);
    }

    @GetMapping("/{username}/display-name")
    public UserDisplayNameDTO getDisplayNameByUsername(@PathVariable String username) {
        return userService.getDisplayNameByUsername(username);
    }

    @PostMapping
    public void createUser(@RequestBody User user, @AuthenticationPrincipal Jwt jwt) {
        userService.createUser(user, jwt);
    }

    @PutMapping
    public void updateUser(@ModelAttribute UpdateUserRequestDto user, @AuthenticationPrincipal Jwt jwt) {
        userService.updateUser(user, jwt);
    }

    @DeleteMapping
    public void deleteUser(@AuthenticationPrincipal Jwt jwt) {
        userService.deleteUser(jwt);
    }

    @GetMapping("/{userId}/books/count")
    public UserNumOfBooksDTO numOfBooks(@PathVariable String userId) {
        return userService.getNumOfBooksByUserId(userId);
    }

    // TODO: Use request DTO, NOT user Model for /create
    // Return proper HTTP responses instead of void
    // Use Optional.orElseThrow instead of manual checks.
    // Right now updateUser mixes ResponseStatusException and RuntimeException — consider making exception handling uniform.
}
