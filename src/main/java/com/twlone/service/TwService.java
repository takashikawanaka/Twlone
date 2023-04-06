package com.twlone.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.entity.Tw;
import com.twlone.entity.User;
import com.twlone.repository.TwRepository;

@Service
public class TwService {
    private final TwRepository twRepository;

    public TwService(TwRepository repository) {
        this.twRepository = repository;
    }

    public Tw getTwById(Integer id) {
        return twRepository.findById(id)
                .get();
    }

    public List<Tw> getTwListByUser(User user) {
        return twRepository.findByUser(user);
    }

    public Integer getCountReTwByTw(Tw tw) {
        return twRepository.countReTwByTw(tw);
    }

    public Integer getCountReplyTwByTw(Tw tw) {
        return twRepository.countReplyTwByTw(tw);
    }

    public Integer getCountFavoriteByTw(Tw tw) {
        return twRepository.countFavoriteByTw(tw);
    }

    @Transactional
    public void saveTw(Tw tw) {
        twRepository.save(tw);
    }
}
