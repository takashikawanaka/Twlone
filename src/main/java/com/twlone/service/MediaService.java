package com.twlone.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.entity.Media;
import com.twlone.repository.MediaRepository;

@Service
public class MediaService {
    private MediaRepository mediaRepository;

    public MediaService(MediaRepository repository) {
        this.mediaRepository = repository;
    }

    public Media getMediaByPath(String path) {
        return mediaRepository.findByPath(path).get();
    }

    @Transactional
    public void saveMedia(Media media) {
        mediaRepository.save(media);
    }
}
