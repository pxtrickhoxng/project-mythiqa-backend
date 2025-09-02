package com.mythiqa.mythiqabackend.dto.response;

public class UploadImgsResponseDTO {
    private String bgImgUrl;
    private String profileImgUrl;

    public UploadImgsResponseDTO() {}

    public UploadImgsResponseDTO(String bgImgUrl, String profileImgUrl) {
        this.bgImgUrl = bgImgUrl;
        this.profileImgUrl = profileImgUrl;
    }

    public String getBgImgUrl() {
        return bgImgUrl;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

}
