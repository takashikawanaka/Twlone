package com.twlone.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostTwDTO {
    private String content;
    private String reTwID;
    private String replyTwID;
    private List<MultipartFile> media;
    private List<String> hashtag;

    public Boolean isIllegale() {
        if (!this.isBlankContent() || !this.isBlankReTwId() || (this.existsMedia() && media.size() <= 4))
            return false;
        return true;
    }

    public Boolean isBlankContent() {
        return content.isBlank();
    }

    public Boolean isBlankReTwId() {
        return reTwID.isBlank();
    }

    public Boolean isBlankReplyTwId() {
        return replyTwID.isBlank();
    }

    public Boolean existsMedia() {
        return media != null;
    }

    public Boolean existsHashTag() {
        return hashtag != null;
    }
}
