package com.twlone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.twlone.entity.HashTag;
import com.twlone.entity.RelatedTwHashTag;
import com.twlone.entity.Tw;

public interface RelatedTwHashTagRepository
        extends JpaRepository<RelatedTwHashTag, Integer>, JpaSpecificationExecutor<Tw> {
    List<RelatedTwHashTag> findByHashtag(HashTag hashtag);
}
