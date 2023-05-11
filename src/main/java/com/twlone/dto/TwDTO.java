package com.twlone.dto;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwDTO {
    private String id;
    private List<List<String>> content;
    private MiniUserDTO user;
    private TwDTO reTw;
    private TwDTO replyTw;
    private ZonedDateTime createdAt;
    private Integer reTwListSize;
    private List<TwDTO> replyTwList;
    private Integer replyTwListSize;
    private Integer favoriteListSize;
    private List<List<String>> mediaList;
    private Boolean isFavorite;
    private Boolean dayHasPassed;
}
