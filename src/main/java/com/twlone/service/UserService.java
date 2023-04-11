package com.twlone.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.dto.MiniUserDTO;
import com.twlone.dto.UserDTO;
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

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public MiniUserDTO convertMiniUserDTO(User user) {
        return MiniUserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .icon(user.getIcon())
                .build();
    }

    public UserDTO.UserDTOBuilder getFullUserDTOBuilder(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .description(user.getDescription())
                .icon(user.getIcon())
                .back(user.getBack())
                .followingListSize(userRepository.countFollowingByUser(user))
                .followerListSize(userRepository.countFollowerByUser(user));
    }
}
