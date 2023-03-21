package com.twlone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twlone.entity.Follow;
import com.twlone.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByUser(User user);

    List<Follow> findByTargetUser(User user);

    Boolean existsByUserAndTargetUser(User user, User targetUser);

    long countByUser(User user);

    long countByTargetUser(User user);

    void deleteByUserAndTargetUser(User user, User targetUser);
}
