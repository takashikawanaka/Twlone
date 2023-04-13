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

    @Transactional
    public void saveFollow(Follow follow) {
        followRepository.save(follow);
    }

    public void saveFollow(User sourceUser, User targetUser) {
        saveFollow(new Follow(sourceUser, targetUser));
    }

    @Transactional
    public void deleteByUserAndTargetUser(User sourceUser, User targetUser) {
        followRepository.deleteBySourceUserAndTargetUser(sourceUser, targetUser);
    }

}
