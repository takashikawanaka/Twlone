package com.twlone.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostUserDTO {
    private final Integer id;
    private String userId;
    private String name;
    private String description;
    private MultipartFile icon;
    private MultipartFile back;

    public Boolean isBlankUserId() {
        return userId.isBlank();
    }

    public Boolean isBlankName() {
        return name.isBlank();
    }

    public Boolean isBlankDescription() {
        return description.isBlank();
    }
}
