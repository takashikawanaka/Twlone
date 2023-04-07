package com.twlone.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Integer id;
    private String userId;
    private String name;
    private String description;
    private String icon;
    private String back;
    private List<TwDTO> twList;
    private Integer followingListSize;
    private Integer followerListSize;
    private Boolean isFollow;
}
