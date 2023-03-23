package com.twlone.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.entity.Favorite;
import com.twlone.entity.Tw;
import com.twlone.entity.User;
import com.twlone.repository.FavoriteRepository;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository repository) {
        this.favoriteRepository = repository;
    }

    public Boolean getBooleanByTwAndUser(Tw tw, User user) {
        return favoriteRepository.existsByTwAndUser(tw, user);
    }

    @Transactional
    public void saveFavorite(Favorite favorite) {
        favoriteRepository.save(favorite);
    }

    public void saveFavorite(Tw tw, User user) {
        saveFavorite(new Favorite(tw, user));
    }

    @Transactional
    public void deleteByTwAndUser(Tw tw, User user) {
        favoriteRepository.deleteByTwAndUser(tw, user);
    }
}
