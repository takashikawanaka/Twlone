package com.twlone.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {
    private final Integer id;
    private final String userId;
    private final String name;
    private final String description;
    private final String icon;
    private final String back;
    private List<TwDTO> twList;
    private final Integer followingListSize;
    private final Integer followerListSize;
    private Boolean isFollow;
}
