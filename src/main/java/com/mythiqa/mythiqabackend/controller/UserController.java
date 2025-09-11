package com.mythiqa.mythiqabackend.controller;

import com.mythiqa.mythiqabackend.dto.request.CreateUserRequestDTO;
import com.mythiqa.mythiqabackend.dto.request.UpdateDisplayNameRequestDTO;
import com.mythiqa.mythiqabackend.dto.request.UpdateUserRequestDto;
import com.mythiqa.mythiqabackend.dto.response.user.UserDisplayNameDTO;
import com.mythiqa.mythiqabackend.dto.response.user.UserNumOfBooksDTO;
import com.mythiqa.mythiqabackend.dto.response.user.UserProfileDTO;
import com.mythiqa.mythiqabackend.dto.response.user.UserProfileImgDTO;
import com.mythiqa.mythiqabackend.repository.BookRepository;
import com.mythiqa.mythiqabackend.repository.UserRepository;
import com.mythiqa.mythiqabackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequestDTO createUserRequest, @AuthenticationPrincipal Jwt jwt) {
        userService.createUser(createUserRequest, jwt);
        URI location = URI.create("/api/users");
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@ModelAttribute UpdateUserRequestDto updateUserRequest, @AuthenticationPrincipal Jwt jwt) {
        userService.updateUser(updateUserRequest, jwt);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Jwt jwt) {
        userService.deleteUser(jwt);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/books/count")
    public UserNumOfBooksDTO numOfBooks(@PathVariable String userId) {
        return userService.getNumOfBooksByUserId(userId);
    }

    @PutMapping("/display-name")
    public ResponseEntity<Void> updateDisplayName(@Valid @RequestBody UpdateDisplayNameRequestDTO updateDisplayNameRequest, @AuthenticationPrincipal Jwt jwt) {
        userService.updateDisplayName(updateDisplayNameRequest, jwt);
        return ResponseEntity.noContent().build();
    }
}
