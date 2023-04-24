package com.twlone.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostTwDTO {
    private String content;
    private Integer reTwID;
    private Integer replyTwID;
    private List<MultipartFile> media;
    private List<String> hashtag;

    public Boolean isIllegale() {
        if (!this.isBlankContent() || this.existsReTwId() || (this.existsMedia() && media.size() <= 4))
            return false;
        return true;
    }

    public Boolean existsReTwId() {
        return reTwID != null;
    }

    public Boolean existsReplyTwId() {
        return replyTwID != null;
    }

    public Boolean existsMedia() {
        return media != null;
    }

    public Boolean existsHashTag() {
        return hashtag != null;
    }

    public Boolean isBlankContent() {
        return content.isBlank();
    }
}
