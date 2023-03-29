package com.twlone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.Media;

public interface MediaRepository extends JpaRepository<Media, Integer> {
    Optional<Media> findByPath(String path);
}
