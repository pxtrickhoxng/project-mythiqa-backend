package com.mythiqa.mythiqabackend.model;

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
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(name = "display_name", nullable = false)
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

    public User(String id, String username, String displayName, String email, String description, String userBackgroundImgUrl, String userProfileImgUrl, LocalDate createdAt, String role) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.description = description;
        this.userBackgroundImgUrl = userBackgroundImgUrl;
        this.userProfileImgUrl = userProfileImgUrl;
        this.createdAt = createdAt;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
