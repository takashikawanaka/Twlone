package com.twlone.repository;

import javax.persistence.criteria.Expression;

import org.springframework.data.jpa.domain.Specification;

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
}
