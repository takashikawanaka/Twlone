package com.twlone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.Follow;
import com.twlone.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Boolean existsByUserAndTargetUser(User user, User targetUser);

    void deleteByUserAndTargetUser(User user, User targetUser);
}
