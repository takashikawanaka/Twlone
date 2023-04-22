package com.twlone.repository;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.twlone.entity.HashTag;
import com.twlone.entity.RelatedTwHashTag;
import com.twlone.entity.Tw;

public class TwSpecification {
    public static Specification<Tw> wordConatins(String word) {
        return (root, query, builder) -> {
            Expression<String> lowerContent = builder.lower(root.get("content"));
            return builder.like(lowerContent, '%' + word.toLowerCase() + '%');
        };
    }

    public static Specification<Tw> deleteFlagEquals(Integer flag) {
        return (root, query, builder) -> {
            return builder.equal(root.get("deleteFlag"), flag);
        };
    }

    public static Specification<Tw> hashtagEquals(HashTag hashtag) {
        return (root, query, builder) -> {
            Join<Tw, RelatedTwHashTag> join = root.join("related_tw_hashtag");
            return builder.equal(join.get("hashtag"), hashtag);
        };
    }
}
