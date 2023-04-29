package com.twlone.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "etw")
public class ETw {

    @Id
    private Integer id;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private String user;
}
