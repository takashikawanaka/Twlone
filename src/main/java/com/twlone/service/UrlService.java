package com.twlone.service;

import org.springframework.stereotype.Service;

import com.twlone.entity.Url;
import com.twlone.repository.UrlRepository;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository repository) {
        this.urlRepository = repository;
    }

    public Url getUrlById(String id) {
        return (urlRepository.findById(id)).get();
    }

    public Url saveUrl(Url url) {
        return urlRepository.save(url);
    }
}
