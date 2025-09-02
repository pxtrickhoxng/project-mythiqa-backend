package com.mythiqa.mythiqabackend.service;

import com.mythiqa.mythiqabackend.dto.request.UpdateUserRequestDto;
import com.mythiqa.mythiqabackend.exception.UsernameAlreadyTakenException;
import com.mythiqa.mythiqabackend.model.User;
import com.mythiqa.mythiqabackend.repository.UserRepository;
import com.mythiqa.mythiqabackend.util.S3Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final ClerkService clerkService;

    public UserService(UserRepository userRepository, FileService fileService, ClerkService clerkService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.clerkService = clerkService;
    }

    @Value("${aws.s3.region}")
    private String region;

    @Transactional
    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        return userRepository.save(user);
    }

    @Transactional
    public void updateUser(UpdateUserRequestDto updateUser, String requesterUserId) {
        Optional<User> existingUser = userRepository.findById(requesterUserId);
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User user = existingUser.get();

        String oldBgImgKey = S3Utils.extractS3ObjectKey(user.getProfileBackgroundImgUrl());
        String oldProfileImgKey = S3Utils.extractS3ObjectKey(user.getUserProfileUrl());

        String bgImgUrl = user.getProfileBackgroundImgUrl();
        String profileImgUrl = user.getUserProfileUrl();

        if (updateUser.getUserBackgroundImgFile() != null && !updateUser.getUserBackgroundImgFile().isEmpty()) {
            String bgImgObjectKey = requesterUserId + "/profile/" + updateUser.getUserBackgroundImgFile().getOriginalFilename();
            fileService.uploadFile(updateUser.getUserBackgroundImgFile(), bgImgObjectKey);
            bgImgUrl = "https://" + fileService.getBucketName() + ".s3." + region + ".amazonaws.com/" + bgImgObjectKey;

            // Delete old background image if it exists
            if (oldBgImgKey != null) {
                fileService.deleteFile(oldBgImgKey);
            }
        }

        if (updateUser.getUserProfileImgFile() != null && !updateUser.getUserProfileImgFile().isEmpty()) {
            String profileImgObjectKey = requesterUserId + "/profile/" + updateUser.getUserProfileImgFile().getOriginalFilename();
            fileService.uploadFile(updateUser.getUserProfileImgFile(), profileImgObjectKey);
            profileImgUrl = "https://" + fileService.getBucketName() + ".s3." + region + ".amazonaws.com/" + profileImgObjectKey;

            // Delete old profile image if it exists
            if (oldProfileImgKey != null) {
                fileService.deleteFile(oldProfileImgKey);
            }
        }


        user.setProfileBackgroundImgUrl(bgImgUrl);
        user.setUserProfileUrl(profileImgUrl);

        if (updateUser.getUsername() != null && !updateUser.getUsername().trim().isEmpty()) {
            try {
                // Update the username in clerk, and only update database if it succeeds
                clerkService.updateClerkUsername(requesterUserId, updateUser.getUsername().trim());
                user.setUsername(updateUser.getUsername().trim());
            } catch (UsernameAlreadyTakenException e) {
                throw e;
            } catch (RuntimeException e) {
                throw new RuntimeException("Failed to update username: " + e.getMessage());
            }
        }

        if (updateUser.getDescription() != null) {
            user.setDescription(updateUser.getDescription().trim());
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String requesterUserId) {
        Optional<User> existingUser = userRepository.findById(requesterUserId);
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(requesterUserId);
    }
}
