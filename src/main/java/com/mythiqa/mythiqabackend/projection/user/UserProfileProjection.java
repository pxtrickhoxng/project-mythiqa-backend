package com.mythiqa.mythiqabackend.projection.user;

import java.time.LocalDate;

public interface UserProfileProjection {
    String getDisplayName();
    String getDescription();
    String getUserProfileImgUrl();
    String getUserBackgroundImgUrl();
    LocalDate getCreatedAt();
}