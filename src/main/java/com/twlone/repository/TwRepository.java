package com.twlone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twlone.entity.Tw;
import com.twlone.entity.User;

public interface TwRepository extends JpaRepository<Tw, Integer> {
    List<Tw> findByUser(User user);

    @Query("SELECT SIZE(t.reTwList) FROM Tw t WHERE t = ?1")
    Integer countReTwByTw(Tw tw);

    @Query("SELECT SIZE(t.replyTwList) FROM Tw t WHERE t = ?1")
    Integer countReplyTwByTw(Tw tw);

    @Query("SELECT SIZE(t.favoriteList) FROM Tw t WHERE t = ?1")
    Integer countFavoriteByTw(Tw tw);
}
