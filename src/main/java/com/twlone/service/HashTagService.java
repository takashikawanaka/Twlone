package com.twlone.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.entity.HashTag;
import com.twlone.repository.HashTagRepository;

@Service
public class HashTagService {
    private final HashTagRepository hashtagRepository;

    public HashTagService(HashTagRepository repository) {
        this.hashtagRepository = repository;
    }

    public Optional<HashTag> getHashTagByName(String name) {
        return hashtagRepository.findByName(name);
    }

    @Transactional
    public HashTag saveHashTag(HashTag hashtag) {
        return hashtagRepository.save(hashtag);
    }
}
