package com.mythiqa.mythiqabackend.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadImgsRequestDTO {
    private MultipartFile bgImgFile;
    private MultipartFile profileImgFile;
    private String bucket;

    public UploadImgsRequestDTO() {}

    public UploadImgsRequestDTO(MultipartFile bgImgFile, MultipartFile profileImgFile, String bucket) {
        this.bgImgFile = bgImgFile;
        this.profileImgFile = profileImgFile;
        this.bucket = bucket;
    }

    public MultipartFile getBgImgFile() {
        return bgImgFile;
    }

    public void setBgImgFile(MultipartFile bgImgFile) {
        this.bgImgFile = bgImgFile;
    }

    public MultipartFile getProfileImgFile() {
        return profileImgFile;
    }

    public void setProfileImgFile(MultipartFile profileImgFile) {
        this.profileImgFile = profileImgFile;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
