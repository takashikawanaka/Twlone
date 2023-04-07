package com.twlone.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MiniUserDTO {
    private String userId;
    private String name;
    private String icon;
}
