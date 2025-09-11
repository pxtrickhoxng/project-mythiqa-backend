package com.mythiqa.mythiqabackend.dto.response.user;


import java.time.LocalDate;

public class UserProfileDTO {
    private final String displayName;
    private final String description;
    private final String userBackgroundImgUrl;
    private final String userProfileImgUrl;
    private final LocalDate createdAt;

    private UserProfileDTO(Builder builder) {
        this.displayName = builder.displayName;
        this.description = builder.description;
        this.userBackgroundImgUrl = builder.userBackgroundImgUrl;
        this.userProfileImgUrl = builder.userProfileImgUrl;
        this.createdAt = builder.createdAt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getUserBackgroundImgUrl() {
        return userBackgroundImgUrl;
    }

    public String getUserProfileImgUrl() {
        return userProfileImgUrl;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public static class Builder {
        private String displayName;
        private String description;
        private String userBackgroundImgUrl;
        private String userProfileImgUrl;
        private LocalDate createdAt;

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder userBackgroundImgUrl(String userBackgroundImgUrl) {
            this.userBackgroundImgUrl = userBackgroundImgUrl;
            return this;
        }

        public Builder userProfileImgUrl(String userProfileImgUrl) {
            this.userProfileImgUrl = userProfileImgUrl;
            return this;
        }

        public Builder createdAt(LocalDate createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserProfileDTO build() {
            return new UserProfileDTO(this);
        }
    }


}
