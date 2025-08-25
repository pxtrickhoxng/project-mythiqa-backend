package com.mythiqa.mythiqabackend.service;

import com.mythiqa.mythiqabackend.dto.request.UpdateUserDto;
import com.mythiqa.mythiqabackend.dto.request.UploadImgsRequestDTO;
import com.mythiqa.mythiqabackend.dto.response.UploadImgsResponseDTO;
import com.mythiqa.mythiqabackend.model.User;
import com.mythiqa.mythiqabackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        return userRepository.save(user);
    }

    @Transactional
    public void updateUser(UpdateUserDto updateUser, String requesterUserId) {
        Optional<User> existingUser = userRepository.findById(requesterUserId);
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User existingUserEntity = existingUser.get();

        if (updateUser.getUsername() != null) {
            existingUserEntity.setUsername(updateUser.getUsername());
        }
        if (updateUser.getUserProfileImgUrl() != null) {
            existingUserEntity.setUserProfileUrl(updateUser.getUserProfileImgUrl());
        }
        if (updateUser.getUserBackgroundImgUrl() != null) {
            existingUserEntity.setProfileBackgroundImgUrl(updateUser.getUserBackgroundImgUrl());
        }
        if (updateUser.getDescription() != null) {
            existingUserEntity.setDescription(updateUser.getDescription());
        }

        userRepository.save(existingUserEntity);
    }

    @Transactional
    public void deleteUser(String requesterUserId) {
        Optional<User> existingUser = userRepository.findById(requesterUserId);
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(requesterUserId);
    }
    // UploadImgsResponseDTO
    @Transactional
    public void uploadImgs(UploadImgsRequestDTO files, String requesterUserId) {
        try {
            String bgImgObjectKey = requesterUserId + "/profile/" + files.getBgImgFile().getOriginalFilename();
            String profileImgObjectKey = requesterUserId + "/profile/" + files.getProfileImgFile().getOriginalFilename();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
