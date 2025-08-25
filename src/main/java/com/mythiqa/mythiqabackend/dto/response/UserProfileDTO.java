package com.mythiqa.mythiqabackend.dto.response;


import java.time.LocalDate;

public class UserProfileDTO {
    private String username;
    private String description;
    private String profileBackgroundImgUrl;
    private String userProfileUrl;
    private LocalDate createdAt;

    public UserProfileDTO () {}

    public UserProfileDTO(String username, String description, String profileBackgroundImgUrl, String userProfileUrl, LocalDate createdAt) {
        this.username = username;
        this.description = description;
        this.profileBackgroundImgUrl = profileBackgroundImgUrl;
        this.userProfileUrl = userProfileUrl;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
