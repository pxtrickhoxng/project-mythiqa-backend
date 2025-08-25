package com.mythiqa.mythiqabackend.controller;

import com.mythiqa.mythiqabackend.dto.request.UpdateUserDto;
import com.mythiqa.mythiqabackend.dto.request.UploadImgsRequestDTO;
import com.mythiqa.mythiqabackend.dto.response.UploadImgsResponseDTO;
import com.mythiqa.mythiqabackend.dto.response.UserProfileDTO;
import com.mythiqa.mythiqabackend.dto.response.UserProfileImgDTO;
import com.mythiqa.mythiqabackend.model.User;
import com.mythiqa.mythiqabackend.repository.UserRepository;
import com.mythiqa.mythiqabackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/id/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/{username}")
    public UserProfileDTO getUserByUsername(@PathVariable String username) {
        return userRepository.findUserProfileByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/{username}/profile-img")
    public UserProfileImgDTO getUserProfileImg(@PathVariable String username) {
        return userRepository.findUserProfileImgByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user, @AuthenticationPrincipal Jwt jwt) {
        String requesterUserId = jwt.getClaim("sub");
        if (requesterUserId == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in JWT.");
        }
        user.setId(requesterUserId);
        return userService.createUser(user);
    }

    // new mappings 8/24/2025
    @PutMapping("/update")
    public void updateUser(@RequestBody UpdateUserDto user, @AuthenticationPrincipal Jwt jwt) {
        String requesterUserId = jwt.getClaim("sub");
        if (requesterUserId == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in JWT.");
        }
        userService.updateUser(user, requesterUserId);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@AuthenticationPrincipal Jwt jwt) {
        String requesterUserId = jwt.getClaim("sub");
        if (requesterUserId == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in JWT.");
        }
        userService.deleteUser(requesterUserId);
    }
    // UploadImgsResponseDTO
    @PostMapping("upload-imgs")
    public void uploadImgs(@ModelAttribute UploadImgsRequestDTO files, @AuthenticationPrincipal Jwt jwt) {
        String requesterUserId = jwt.getClaim("sub");
        if (requesterUserId == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in JWT.");
        }
         userService.uploadImgs(files, requesterUserId);
    }

}
