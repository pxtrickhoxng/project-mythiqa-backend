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

    @Column(nullable = false)
    private String email;

    private String description;

    @Column(name = "profile_background_img_url")
    private String profileBackgroundImgUrl;

    @Column(name = "user_profile_url")
    private String userProfileUrl;

    @Column(name = "created_at")
    private LocalDate createdAt;

    private String role;

    public User() {}

    public User(String id, String username, String email, String description, String profileBackgroundImgUrl, String userProfileUrl, LocalDate createdAt, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.description = description;
        this.profileBackgroundImgUrl = profileBackgroundImgUrl;
        this.userProfileUrl = userProfileUrl;
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

    public String getProfileBackgroundImgUrl() {
        return profileBackgroundImgUrl;
    }

    public void setProfileBackgroundImgUrl(String profileBackgroundImgUrl) {
        this.profileBackgroundImgUrl = profileBackgroundImgUrl;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
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
