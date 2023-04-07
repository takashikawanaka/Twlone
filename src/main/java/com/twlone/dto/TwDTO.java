package com.twlone.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.twlone.entity.Media;
import com.twlone.entity.Tw;
import com.twlone.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TwDTO {
    private final Integer id;
    private final String content;
    private final User user;
    private final TwDTO reTw;
    private final TwDTO replyTw;
    private final LocalDateTime createdAt;
    private final Integer reTwListSize;
    @Builder.Default
    private final List<TwDTO> replyTwList = null;
    private final Integer replyTwListSize;
    private final Integer favoriteListSize;
    private final List<Media> mediaList;
    // private List<RelatedTwHashTag> hashTagList;
    @Builder.Default
    private final Boolean isFavorite = false;
    @Builder.Default
    private final Boolean dayHasPassed = false;

}
