package com.twlone.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.entity.HashTag;
import com.twlone.entity.RelatedTwHashTag;
import com.twlone.entity.Tw;
import com.twlone.repository.RelatedTwHashTagRepository;

@Service
public class RelatedTwHashTagService {
    private final RelatedTwHashTagRepository relatedTwHashTagRepository;

    public RelatedTwHashTagService(RelatedTwHashTagRepository repository) {
        this.relatedTwHashTagRepository = repository;
    }

    public List<RelatedTwHashTag> getRelatedTwHashTagByHashTag(HashTag hashtag) {
        return relatedTwHashTagRepository.findByHashtag(hashtag);
    }

    public List<Tw> getTwLIstByHashTag(HashTag hashtag) {
        return relatedTwHashTagRepository.findTwListByHashTag(hashtag);
    }

    @Transactional
    public void saveRelatedTwHashTag(RelatedTwHashTag relatedTwHashTag) {
        relatedTwHashTagRepository.save(relatedTwHashTag);
    }
}
