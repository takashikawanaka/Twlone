package com.twlone.entity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "etw")
public class ETw {

    @Id
    private String id;

    @Field(type = FieldType.Text, index = true)
    private String content;

    @Field(name = "user_id", type = FieldType.Integer, index = true)
    private Integer userId;

    @Field(name = "reETw_id", type = FieldType.Keyword, index = true)
    private String reETwId;

    public Boolean existsReETwId() {
        return this.reETwId != null;
    }

    @Field(name = "replyETw_id", type = FieldType.Keyword, index = true)
    private String replyETwId;

    public Boolean existsReplyETwId() {
        return this.replyETwId != null;
    }

    @Field(name = "created_at", type = FieldType.Date, index = true)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Field(name = "delete_flag", type = FieldType.Integer, index = false)
    private Integer deleteFlag = 0;

    @Field(name = "isfavorite", type = FieldType.Boolean, index = false)
    private Boolean isFavorite;

    protected ETw() {
    }

    public ETw(Integer userId) {
        this.userId = userId;
    }

    @Field(name = "favorite_user_list", type = FieldType.Integer, index = true)
    private List<Integer> favoriteUserList;

    @Field(name = "media_list", type = FieldType.Keyword, index = false)
    private List<String> mediaList;

    @Field(name = "hashtag_list", type = FieldType.Keyword, index = true)
    private List<String> hashtagList;

    @Field(name = "url_list", type = FieldType.Keyword, index = false)
    private List<String> urlList;

    @Field(name = "reETw_list_size", type = FieldType.Integer, index = false)
    private Integer reETwListSize;

    @Field(name = "replyETw_list_size", type = FieldType.Integer, index = false)
    private Integer replyETwListSize;

    @Field(name = "favorite_user_list_size", type = FieldType.Integer, index = false)
    private Integer favoriteUserListSize;

    @Field(name = "hashtag_list_size", type = FieldType.Integer, index = false)
    private Integer hashtagListSize;

    public void InitETw() {
        this.favoriteUserList = new ArrayList<>();
        this.reETwListSize = 0;
        this.replyETwListSize = 0;
        this.favoriteUserListSize = 0;
        this.hashtagListSize = 0;
    }

    public Boolean isOnlyReETw() {
        return this.content == null && this.reETwId != null && mediaList == null;
    }
}
