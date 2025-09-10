package com.mythiqa.mythiqabackend.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class UpdateUserRequestDto {
    private String displayName;
    private String description;
    private MultipartFile userBackgroundImgFile;
    private MultipartFile userProfileImgFile;

    public UpdateUserRequestDto() {}

    public UpdateUserRequestDto(String displayName, String description, MultipartFile userBackgroundImgFile, MultipartFile userProfileImgFile) {
        this.displayName = displayName;
        this.description = description;
        this.userBackgroundImgFile = userBackgroundImgFile;
        this.userProfileImgFile = userProfileImgFile;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
