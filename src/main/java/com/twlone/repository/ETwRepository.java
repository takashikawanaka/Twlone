package com.twlone.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.twlone.entity.ETw;

public interface ETwRepository extends ElasticsearchRepository<ETw, Integer> {

}
