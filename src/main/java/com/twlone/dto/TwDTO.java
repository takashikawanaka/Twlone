package com.twlone.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.twlone.entity.Media;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwDTO {
    private Integer id;
    private List<List<String>> content;
    private MiniUserDTO user;
    private TwDTO reTw;
    private TwDTO replyTw;
    private LocalDateTime createdAt;
    private Integer reTwListSize;
    private List<TwDTO> replyTwList;
    private Integer replyTwListSize;
    private Integer favoriteListSize;
    private List<Media> mediaList;
    private Boolean isFavorite;
    private Boolean dayHasPassed;
}
