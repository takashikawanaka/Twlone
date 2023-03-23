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
        return twRepository.findById(id).get();
    }

    public List<Tw> getTwListByUser(User user) {
        return twRepository.findByUser(user);
    }

    @Transactional
    public void saveTw(Tw tw) {
        twRepository.save(tw);
    }

    public void saveTw(User user, String content) {
        saveTw(new Tw(user, content));
    }

}
