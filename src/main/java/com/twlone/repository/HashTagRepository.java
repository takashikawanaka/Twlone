package com.twlone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.HashTag;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {
    Optional<HashTag> findByName(String name);
}
