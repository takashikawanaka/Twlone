package com.twlone.dto;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostTw {
    private String content;
    private Optional<Integer> reTwID;
    private Optional<Integer> replyTwID;
    private List<MultipartFile> media;

    public Boolean isIllegale() {
        if (!isBlankContent() || reTwID.isPresent() || media != null)
            return false;
        return true;
    }

    public Boolean isBlankContent() {
        return content.isBlank();
    }

    public Optional<List<MultipartFile>> getMedia() {
        return Optional.ofNullable(media);
    }
}
