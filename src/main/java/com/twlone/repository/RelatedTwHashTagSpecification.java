package com.twlone.repository;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.twlone.entity.HashTag;
import com.twlone.entity.RelatedTwHashTag;
import com.twlone.entity.Tw;

public class RelatedTwHashTagSpecification {
    public static Specification<RelatedTwHashTag> hashtagEquals(HashTag hashtag) {
        return (root, query, builder) -> {
            return builder.equal(root.get("hashtag"), hashtag);
        };
    }

    public static Specification<RelatedTwHashTag> deleteFlagEquals(Integer flag) {
        return (root, query, builder) -> {
            Join<RelatedTwHashTag, Tw> join = root.join("tw");
            return builder.equal(join.get("deleteFlag"), flag);
        };
    }
}
