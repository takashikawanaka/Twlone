package com.twlone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twlone.dto.TwDTODTO;
import com.twlone.entity.Media;
import com.twlone.entity.Tw;
import com.twlone.entity.User;

public interface TwRepository extends JpaRepository<Tw, Integer> {
    List<Tw> findByUser(User user);

    @Query("SELECT t FROM Tw t WHERE t.user.id = ?1 AND t.replyTw IS NULL AND t.deleteFlag = 0 ORDER BY t.id desc")
    List<Tw> findByUserDTO(Integer id);

    @Query("SELECT new com.twlone.dto.TwDTODTO(t.id, t.content, t.user, t.reTw.id, t.replyTw.id, t.createdAt,"
            + "SIZE(t.reTwList), SIZE(t.replyTwList), SIZE(t.favoriteList), SIZE(t.mediaList), SIZE(t.hashtagList))"
            + "FROM Tw t WHERE t.user.id = ?1 AND t.replyTw IS NULL AND t.deleteFlag = 0 ORDER BY t.id desc")
    List<TwDTODTO> findTwDTODTOListByUserDTO(Integer id);

    @Query("SELECT new com.twlone.dto.TwDTODTO(t.id, t.content, t.user, t.reTw.id, t.replyTw.id, t.createdAt,"
            + "SIZE(t.reTwList), SIZE(t.replyTwList), SIZE(t.favoriteList), SIZE(t.mediaList), SIZE(t.hashtagList))"
            + "FROM Tw t WHERE t.id = ?1")
    Optional<TwDTODTO> findTwDTODTOById(Integer id);

    @Query("SELECT new com.twlone.dto.TwDTODTO(t.id, t.content, t.user, t.reTw.id, t.replyTw.id, t.createdAt,"
            + "SIZE(t.reTwList), SIZE(t.replyTwList), SIZE(t.favoriteList), SIZE(t.mediaList), SIZE(t.hashtagList))"
            + "FROM Tw t WHERE t.replyTw.id= ?1 AND t.deleteFlag = 0 ORDER BY t.id desc")
    List<TwDTODTO> findTwDTODTOListByReplyTwId(Integer id);

    @Query("SELECT m FROM Media m WHERE m.tw.id = ?1")
    List<Media> findMediaByTw(Integer id);

    @Query("SELECT SIZE(t.reTwList), SIZE(t.replyTwList), SIZE(t.favoriteList), SIZE(t.hashtagList), SIZE(t.mediaList) FROM Tw t WHERE t = ?1")
    Object countListByTw(Tw tw);

    @Query("SELECT COUNT(f) = 1 FROM Favorite f WHERE f.tw.id = ?1 AND f.user = ?2")
    Boolean existsFavoriteByTwIDAndUser(Integer twid, User user);
}
