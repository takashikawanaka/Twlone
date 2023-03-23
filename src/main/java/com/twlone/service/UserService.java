package com.twlone.service;

import java.util.List;

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

    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    public User getUserByUserId(String id) {
        return userRepository.findByUserId(id).get();
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
