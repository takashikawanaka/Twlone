package com.twlone.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.entity.RelatedTwHashTag;
import com.twlone.repository.RelatedTwHashTagRepository;

@Service
public class RelatedTwHashTagService {
    private final RelatedTwHashTagRepository relatedTwHashTagRepository;

    public RelatedTwHashTagService(RelatedTwHashTagRepository repository) {
        this.relatedTwHashTagRepository = repository;
    }

    @Transactional
    public void saveRelatedTwHashTag(RelatedTwHashTag relatedTwHashTag) {
        relatedTwHashTagRepository.save(relatedTwHashTag);
    }
}
