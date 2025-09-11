package com.mythiqa.mythiqabackend.dto.response.user;

public class UserProfileImgDTO {
    private String userProfileImgUrl;

    public UserProfileImgDTO() {}

    public UserProfileImgDTO(String userProfileImgUrl) {
        this.userProfileImgUrl = userProfileImgUrl;
    }

    public String getUserProfileImgUrl() {
        return userProfileImgUrl;
    }

    public void setUserProfileImgUrl(String userProfileImgUrl) {
        this.userProfileImgUrl = userProfileImgUrl;
    }
}
