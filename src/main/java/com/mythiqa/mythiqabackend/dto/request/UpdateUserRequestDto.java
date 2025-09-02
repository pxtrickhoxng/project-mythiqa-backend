package com.mythiqa.mythiqabackend.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class UpdateUserRequestDto {
    private String username;
    private String description;
    private MultipartFile userBackgroundImgFile;
    private MultipartFile userProfileImgFile;

    public UpdateUserRequestDto() {}

    public UpdateUserRequestDto(String username, String description, MultipartFile userBackgroundImgFile, MultipartFile userProfileImgFile) {
        this.username = username;
        this.description = description;
        this.userBackgroundImgFile = userBackgroundImgFile;
        this.userProfileImgFile = userProfileImgFile;
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

    public MultipartFile getUserBackgroundImgFile() {
        return userBackgroundImgFile;
    }

    public void setUserBackgroundImgFile(MultipartFile userBackgroundImgFile) {
        this.userBackgroundImgFile = userBackgroundImgFile;
    }

    public MultipartFile getUserProfileImgFile() {
        return userProfileImgFile;
    }

    public void setUserProfileImgFile(MultipartFile userProfileImgFile) {
        this.userProfileImgFile = userProfileImgFile;
    }
}
