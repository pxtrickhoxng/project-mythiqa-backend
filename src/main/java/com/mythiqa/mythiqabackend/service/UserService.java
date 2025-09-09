package com.mythiqa.mythiqabackend.service;

import com.mythiqa.mythiqabackend.dto.request.UpdateUserRequestDto;
import com.mythiqa.mythiqabackend.dto.response.UserDisplayNameDTO;
import com.mythiqa.mythiqabackend.dto.response.UserNumOfBooksDTO;
import com.mythiqa.mythiqabackend.dto.response.UserProfileDTO;
import com.mythiqa.mythiqabackend.dto.response.UserProfileImgDTO;
import com.mythiqa.mythiqabackend.exception.UsernameAlreadyTakenException;
import com.mythiqa.mythiqabackend.model.User;
import com.mythiqa.mythiqabackend.projection.user.UserDisplayNameProjection;
import com.mythiqa.mythiqabackend.projection.user.UserProfileImgProjection;
import com.mythiqa.mythiqabackend.projection.user.UserProfileProjection;
import com.mythiqa.mythiqabackend.repository.BookRepository;
import com.mythiqa.mythiqabackend.repository.UserRepository;
import com.mythiqa.mythiqabackend.util.S3Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final FileService fileService;
    private final ClerkService clerkService;

    public UserService(UserRepository userRepository, BookRepository bookRepository, FileService fileService, ClerkService clerkService) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.fileService = fileService;
        this.clerkService = clerkService;
    }

    @Value("${aws.s3.region}")
    private String region;

    public boolean checkUserExistsById(String userId) {
        return userRepository.existsById(userId);
    }

    public UserProfileDTO getUserProfileByUsername (String username) {
        UserProfileProjection projection = userRepository.findUserProfileByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserProfileDTO.Builder()
                .displayName(projection.getDisplayName())
                .description(projection.getDescription())
                .userBackgroundImgUrl(projection.getUserBackgroundImgUrl())
                .userProfileImgUrl(projection.getUserProfileImgUrl())
                .createdAt(projection.getCreatedAt())
                .build();
    }

    @Transactional
    public void createUser(User user, Jwt jwt) {
        String requesterUserId = jwt.getClaim("sub");
        if (requesterUserId == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in JWT.");
        }
        user.setId(requesterUserId);
        boolean userExists = userRepository.existsById(user.getId());
        if (userExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        user.setCreatedAt(LocalDate.now());
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(UpdateUserRequestDto updateUser, Jwt jwt) {
        String requesterUserId = jwt.getClaim("sub");
        if (requesterUserId == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in JWT.");
        }

        Optional<User> existingUser = userRepository.findById(requesterUserId);
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User user = existingUser.get();

        String oldBgImgKey = S3Utils.extractS3ObjectKey(user.getUserBackgroundImgUrl());
        String oldProfileImgKey = S3Utils.extractS3ObjectKey(user.getUserProfileImgUrl());

        String bgImgUrl = user.getUserBackgroundImgUrl();
        String profileImgUrl = user.getUserProfileImgUrl();

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

        user.setUserBackgroundImgUrl(bgImgUrl);
        user.setUserProfileImgUrl(profileImgUrl);

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
    public void deleteUser(Jwt jwt) {
        String requesterUserId = jwt.getClaim("sub");
        if (requesterUserId == null || requesterUserId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User ID not found in JWT.");
        }

        boolean userExists = userRepository.existsById(requesterUserId);
        if (!userExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(requesterUserId);
    }

    public UserProfileImgDTO getProfileImgByUsername(String username) {
        UserProfileImgProjection projection = userRepository.findUserProfileImgByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserProfileImgDTO(projection.getUserProfileImgUrl());
    }

    public UserDisplayNameDTO getDisplayNameByUsername(String username) {
        UserDisplayNameProjection projection = userRepository.findDisplayNameByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserDisplayNameDTO(projection.getDisplayName());
    }

    public UserNumOfBooksDTO getNumOfBooksByUserId(String userId) {
        long bookCount = bookRepository.countByUserId(userId);
        return new UserNumOfBooksDTO(bookCount);
    }
}
