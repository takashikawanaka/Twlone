package com.twlone.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.twlone.entity.Tw;
import com.twlone.entity.User;

import lombok.Data;

@Data
public class TwDTODTO {
    private final Integer id;
    private final String content;
    private final User user;
    private final Integer reTwId;
    private final Integer replyTwId;
    private final LocalDateTime createdAt;
    private final Integer reTwListSize;
    private final Integer replyTwListSize;
    private final Integer favoriteListSize;
    private final Integer mediaListSize;
    private final Integer hashtagListSize;

    public Boolean dayHasPassed(LocalDate date) {
        return this.createdAt.toLocalDate()
                .isEqual(date);
    }
}
