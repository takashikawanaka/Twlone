package com.twlone.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "url")
public class Url {
    @Id
    private String id;

    @Field(type = FieldType.Keyword, index = true)
    private String url;

    protected Url() {

    }

    public Url(String url) {
        this.url = url;
    }
}
