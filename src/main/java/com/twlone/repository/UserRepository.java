package com.twlone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.twlone.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(String userId);

    @Query("SELECT SIZE(u.followingList) FROM User u WHERE u = ?1")
    Integer countFollowingByUser(User user);

    @Query("SELECT SIZE(u.followerList) FROM User u WHERE u = ?1")
    Integer countFollowerByUser(User user);

    @Query("SELECT COUNT(f) = 1 FROM Follow f WHERE f.sourceUser = ?1 AND f.targetUser = ?2")
    Boolean existsFollowBySourceUserAndTargetUser(User sourceUser, User targetUser);
}
