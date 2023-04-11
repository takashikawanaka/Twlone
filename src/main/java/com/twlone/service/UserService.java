package com.twlone.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.entity.User;
import com.twlone.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    // Will Remove
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUserId(String id) {
        return userRepository.findByUserId(id);
    }

    public Integer getCountFollowingByUser(User user) {
        return userRepository.countFollowingByUser(user);
    }

    public Integer getCountFollowerByUser(User user) {
        return userRepository.countFollowerByUser(user);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
