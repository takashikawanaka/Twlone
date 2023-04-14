package com.twlone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twlone.entity.HashTag;
import com.twlone.entity.RelatedTwHashTag;
import com.twlone.entity.Tw;

public interface RelatedTwHashTagRepository extends JpaRepository<RelatedTwHashTag, Integer> {
    List<RelatedTwHashTag> findByHashtag(HashTag hashtag);

    @Query("SELECT r.tw FROM RelatedTwHashTag r WHERE r.hashtag = ?1")
    List<Tw> findTwListByHashTag(HashTag hashtag);
}
