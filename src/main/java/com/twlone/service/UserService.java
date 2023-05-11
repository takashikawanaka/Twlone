package com.twlone.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.twlone.dto.MiniUserDTO;
import com.twlone.dto.PostUserDTO;
import com.twlone.dto.UserDTO;
import com.twlone.entity.User;
import com.twlone.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    // Will Remove
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public List<Integer> getFollowingIdListByUser(User user) {
        return userRepository.findFollowingIdListByUser(user);
    }

    public List<MiniUserDTO> getUserListByRandomId() {
        Integer count = userRepository.countUser();
        Random random = new Random();
        List<Integer> randomList = new ArrayList<>();
        for (Integer i = 0; i < 4; i++) {
            randomList.add(random.nextInt(count) + 1);
        }
        return userRepository.findUserListByRandomId(randomList);
    }

    public Boolean getExistsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<UserDTO> getUserDTOByUserId(String id) {
        return userRepository.findUserDTOByUser(id);
    }

    public boolean getBooleanFollowBySourceUserAndTargetUserID(User sourceUser, UserDTO targetUser) {
        return userRepository.existsFollowBySourceUserAndTargetUserDTO(sourceUser, targetUser.getId());
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user, PostUserDTO postUser) throws IOException {
        if (!postUser.isBlankName())
            user.setName(postUser.getName());
        if (!postUser.isBlankUserId())
            user.setUserId((postUser.getUserId()));
        if (!postUser.isBlankDescription())
            user.setDescription(postUser.getDescription());
        if (!postUser.isEmptyIcon()) {
            String fileName = user.getId() + formatter.format(LocalDateTime.now()) + "." + postUser.getIconExtention();
            try (InputStream inputStream = (postUser.getIcon()).getInputStream();
                    OutputStream outputStream = Files.newOutputStream(Paths.get("./icons", fileName))) {
                inputStream.transferTo(outputStream);
            }
            user.setIcon(fileName);
        }
        if (!postUser.isEmptyBack()) {
            String fileName = user.getId() + formatter.format(LocalDateTime.now()) + "." + postUser.getBackExtention();
            try (InputStream inputStream = (postUser.getBack()).getInputStream();
                    OutputStream outputStream = Files.newOutputStream(Paths.get("./backs", fileName))) {
                inputStream.transferTo(outputStream);
            }
            user.setBack(fileName);
        }
        return this.saveUser(user);
    }

    public MiniUserDTO convertMiniUserDTO(User user) {
        return new MiniUserDTO(user.getUserId(), user.getName(), user.getIcon());
    }

    public MiniUserDTO convertMiniUserDTO(Integer id) {
        return this.convertMiniUserDTO((this.getUserById(id)).get());
    }
}
