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

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Integer)
    private Integer user_id;

    @Field(type = FieldType.Keyword)
    private String reTw_id;

    @Field(type = FieldType.Keyword)
    private String replyTw_id;

    @Field(type = FieldType.Date)
    private ZonedDateTime created_at = ZonedDateTime.now();

    protected ETw() {
    }

    public ETw(String id) {
        this.id = id;
    }

    public ETw(Integer user_id) {
        this.user_id = user_id;
    }

    @Field(type = FieldType.Integer)
    private List<Integer> favorite_user_list = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private List<String> media_list;

    @Field(type = FieldType.Keyword)
    private List<String> hashtag_list;
}
