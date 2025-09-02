package com.mythiqa.mythiqabackend.dto.response;

public class UserProfileAndBackgroundImagesDTO {
    private String profileBackgroundImgUrl;
    private String userProfileUrl;

    public UserProfileAndBackgroundImagesDTO() {}

    public UserProfileAndBackgroundImagesDTO(String profileBackgroundImgUrl, String userProfileUrl) {
        this.profileBackgroundImgUrl = profileBackgroundImgUrl;
        this.userProfileUrl = userProfileUrl;
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
}
