package com.mythiqa.mythiqabackend.dto.request;

public class UpdateUserDto {
    private String username;
    private String userProfileImgUrl;
    private String userBackgroundImgUrl;
    private String description;

    public UpdateUserDto() {}

    public UpdateUserDto(String username, String userProfileImgUrl, String userBackgroundImgUrl, String description) {
        this.username = username;
        this.userProfileImgUrl = userProfileImgUrl;
        this.userBackgroundImgUrl = userBackgroundImgUrl;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserProfileImgUrl() {
        return userProfileImgUrl;
    }

    public void setUserProfileImgUrl(String userProfileImgUrl) {
        this.userProfileImgUrl = userProfileImgUrl;
    }

    public String getUserBackgroundImgUrl() {
        return userBackgroundImgUrl;
    }

    public void setUserBackgroundImgUrl(String userBackgroundImgUrl) {
        this.userBackgroundImgUrl = userBackgroundImgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
