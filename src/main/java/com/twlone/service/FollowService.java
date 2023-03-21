package com.twlone.service;

import java.util.List;
import java.util.Optional;

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

    public List<Follow> getFollowListByUser(User user) {
        return followRepository.findByUser(user);
    }

    public List<Follow> getFollowListByTargetUser(User user) {
        return followRepository.findByTargetUser(user);
    }

    public Boolean getBooleanByUserIdAndTargetUserId(User user, User targetUser) {
        return followRepository.existsByUserAndTargetUser(user, targetUser);
    }

    public long getFollowingCountByUserId(User user) {
        return followRepository.countByUser(user);
    }

    public long getFollowerCountByTargetUserId(User user) {
        return followRepository.countByTargetUser(user);
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
