package com.mythiqa.mythiqabackend.dto.response.user;

public class UserDisplayNameDTO {
    private String userDisplayName;

    public UserDisplayNameDTO() {}

    public UserDisplayNameDTO(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }
}
