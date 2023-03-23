package com.twlone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.Favorite;
import com.twlone.entity.Tw;
import com.twlone.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Boolean existsByTwAndUser(Tw tw, User user);

    void deleteByTwAndUser(Tw tw, User user);
}
