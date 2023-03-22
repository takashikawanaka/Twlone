package com.twlone.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.entity.Follow;
import com.twlone.entity.User;
import com.twlone.repository.FollowRepository;

@Service
public class FollowService {
    private final FollowRepository followRepository;

    public FollowService(FollowRepository repository) {
        this.followRepository = repository;
    }

    public Boolean getBooleanByUserIdAndTargetUser(User user, User targetUser) {
        return followRepository.existsByUserAndTargetUser(user, targetUser);
    }

    @Transactional
    public Follow saveFollow(Follow follow) {
        return followRepository.save(follow);
    }

    @Transactional
    public void deleteByUserAndTargetUser(User user, User targetUser) {
        followRepository.deleteByUserAndTargetUser(user, targetUser);
    }

}
