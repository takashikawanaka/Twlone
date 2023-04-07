package com.twlone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.Follow;
import com.twlone.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Boolean existsBySourceUserAndTargetUser(User sourceUser, User targetUser);

    void deleteBySourceUserAndTargetUser(User sourceUser, User targetUser);
}
