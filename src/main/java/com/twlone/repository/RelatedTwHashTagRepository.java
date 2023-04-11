package com.twlone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.HashTag;
import com.twlone.entity.RelatedTwHashTag;

public interface RelatedTwHashTagRepository extends JpaRepository<RelatedTwHashTag, Integer> {
    List<RelatedTwHashTag> findByHashtag(HashTag hashtag);
}
