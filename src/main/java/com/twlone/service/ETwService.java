package com.twlone.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.twlone.dto.PostTwDTO;
import com.twlone.entity.ETw;
import com.twlone.entity.Media.MediaType;
import com.twlone.repository.ETwRepository;

@Service
public class ETwService {
    private final ETwRepository eTwRepository;
    private ElasticsearchOperations elasticsearchOperations;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public ETwService(ETwRepository repository, ElasticsearchOperations operations) {
        this.eTwRepository = repository;
        this.elasticsearchOperations = operations;
    }

    public ETw getETwById(String eTw_id) {
        return elasticsearchOperations.get(eTw_id, ETw.class);
    }

    public Boolean existsETwById(String eTw_id) {
        return elasticsearchOperations.exists(eTw_id, IndexCoordinates.of("etw"));
    }

    public void savePostTwDTO(UserDetail userDetail, PostTwDTO postTw) throws IllegalArgumentException, IOException {
        ETw eTw = new ETw((userDetail.getUser()).getId());
        if (!postTw.isBlankContent()) {
            if (postTw.existsHashTag()) {
                List<String> hashtagList = new ArrayList<>();
                for (String name : postTw.getHashtag()) {
                    hashtagList.add(name);
                }
                eTw.setHashtag_list(hashtagList);
            }
            eTw.setContent(postTw.getContent());
        }
        if (!postTw.isBlankReTwId()) {
            if (!this.existsETwById(postTw.getReTwID()))
                throw new IllegalArgumentException("NotFount ReTwID");
            eTw.setReTw_id(postTw.getReTwID());
        }
        if (!postTw.isBlankReplyTwId()) {
            if (!this.existsETwById(postTw.getReplyTwID()))
                throw new IllegalArgumentException("NotFount ReplyTwID");
            eTw.setReplyTw_id(postTw.getReplyTwID());
        }
        if (postTw.existsMedia())
            eTw.setMedia_list(this.saveMedias(postTw.getMedia(), eTw));
        elasticsearchOperations.save(eTw, IndexCoordinates.of("etw"));
    }

    // Remove
    public void saveETw(ETw eTw) {
        elasticsearchOperations.save(eTw, IndexCoordinates.of("etw"));
    }

    // Question
    public void deleteETw(String eTw_id) {
        elasticsearchOperations.delete(eTw_id, IndexCoordinates.of("etw"));
    }

    public List<String> saveMedias(List<MultipartFile> medias, ETw eTw) throws IllegalArgumentException, IOException {
        List<String> mediaList = new ArrayList<>();
        for (int i = 0; i < medias.size(); i++) {
            MultipartFile media = medias.get(i);
            String extention = (media.getContentType()).split("/")[1];
            // Check contained IllegalArgumentException
            MediaType.valueOf(extention);
            String filePath = eTw.getUser_id() + formatter.format(LocalDateTime.now()) + i + "." + extention;
            try (InputStream inputStream = media.getInputStream();
                    OutputStream outputStream = Files.newOutputStream(Paths.get("./medias", filePath))) {
                inputStream.transferTo(outputStream);
            }
            mediaList.add(filePath);
        }
        return mediaList;
    }

    public void favoriteETw(String eTw_id, Integer user_id) {
        UpdateQuery updateQuery = UpdateQuery.builder(eTw_id)
                .withScriptType(ScriptType.INLINE)
                .withLang("painless")
                .withScript("if(!ctx._source.favorite_list.contains(params.user))"
                        + "{ ctx._source.favorite_list.add(params.user) }")
                .withParams(Collections.singletonMap("user", user_id))
                .build();
        elasticsearchOperations.update(updateQuery, IndexCoordinates.of("etw"));
    }

    public void unfavoriteETw(String eTw_id, Integer user_id) {
        UpdateQuery updateQuery = UpdateQuery.builder(eTw_id)
                .withScriptType(ScriptType.INLINE)
                .withLang("painless")
                .withScript("ctx._source.favorite_list.removeIf(id -> id == params.user)")
                .withParams(Collections.singletonMap("user", user_id))
                .build();
        elasticsearchOperations.update(updateQuery, IndexCoordinates.of("etw"));
    }
}