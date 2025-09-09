package com.mythiqa.mythiqabackend.dto.response;

public class UserProfileAndBackgroundImagesDTO {
    private String userBackgroundImgUrl;
    private String userProfileImgUrl;

    public UserProfileAndBackgroundImagesDTO() {}

    public UserProfileAndBackgroundImagesDTO(String userBackgroundImgUrl, String userProfileImgUrl) {
        this.userBackgroundImgUrl = userBackgroundImgUrl;
        this.userProfileImgUrl = userProfileImgUrl;
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
}
