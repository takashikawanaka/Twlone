package com.twlone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twlone.entity.Tw;
import com.twlone.entity.User;

public interface TwRepository extends JpaRepository<Tw, Integer> {
    List<Tw> findByUser(User user);

    @Query("SELECT t FROM Tw t WHERE t.user.id = ?1 AND t.replyTw IS NULL AND deleteFlag = 0 ORDER BY t.id desc")
    List<Tw> findByUserDTO(Integer id);

    @Query("SELECT SIZE(t.reTwList), SIZE(t.replyTwList), SIZE(t.favoriteList), SIZE(t.hashtagList) FROM Tw t WHERE t = ?1")
    Object countListByTw(Tw tw);

    @Query("SELECT COUNT(f) = 1 FROM Favorite f WHERE f.tw = ?1 AND f.user = ?2")
    Boolean existsFavoriteByTwAndUser(Tw tw, User user);
}
