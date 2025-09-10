package com.mythiqa.mythiqabackend.model;

import com.mythiqa.mythiqabackend.dto.request.CreateUserRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column(name = "display_name")
    private String displayName;

    @Column(nullable = false)
    private String email;

    private String description;

    @Column(name = "profile_background_img_url")
    private String userBackgroundImgUrl;

    @Column(name = "user_profile_url")
    private String userProfileImgUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    private String role;

    public User() {}

    public User(String userId, String username, String displayName, String email, String description, String userBackgroundImgUrl, String userProfileImgUrl, LocalDate createdAt, String role) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.description = description;
        this.userBackgroundImgUrl = userBackgroundImgUrl;
        this.userProfileImgUrl = userProfileImgUrl;
        this.createdAt = createdAt;
        this.role = role;
    }

    public User(CreateUserRequestDTO dto, String userId) {
        this.userId = userId;
        this.username = dto.getUsername();
        this.displayName = dto.getDisplayName();
        this.email = dto.getEmail();
        this.description = dto.getDescription();
        this.userBackgroundImgUrl = dto.getUserBackgroundImgUrl();
        this.userProfileImgUrl = dto.getUserProfileImgUrl();
        this.role = dto.getRole();
        this.createdAt = LocalDate.now();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserBackgroundImgUrl() {
        return userBackgroundImgUrl;
    }

    public void setUserBackgroundImgUrl(String userBackgroundImgUrl) {
        this.userBackgroundImgUrl = userBackgroundImgUrl;
    }

    public String getUserProfileImgUrl() {
        return userProfileImgUrl;
    }

    public void setUserProfileImgUrl(String userProfileImgUrl) {
        this.userProfileImgUrl = userProfileImgUrl;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
