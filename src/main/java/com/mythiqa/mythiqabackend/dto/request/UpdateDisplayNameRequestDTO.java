package com.mythiqa.mythiqabackend.dto.request;

import jakarta.validation.constraints.NotNull;

public class UpdateDisplayNameRequestDTO {
    @NotNull
    private String displayName;

    public UpdateDisplayNameRequestDTO() {}

    public UpdateDisplayNameRequestDTO(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
