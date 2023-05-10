package com.twlone.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.twlone.entity.Url;

public interface UrlRepository extends ElasticsearchRepository<Url, String> {

}
