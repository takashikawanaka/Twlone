package com.twlone.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.twlone.dto.PostTwDTO;
import com.twlone.dto.PostUserDTO;
import com.twlone.entity.User;
import com.twlone.service.ETwService;
import com.twlone.service.FollowService;
import com.twlone.service.UserDetail;
import com.twlone.service.UserService;

@RestController
@RequestMapping("user")
public class UserPostController {
    private final UserService userService;
    private final FollowService followService;
    private final ETwService eTwService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public UserPostController(UserService service, FollowService service2, ETwService service3) {
        this.userService = service;
        this.followService = service2;
        this.eTwService = service3;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void postUser(@AuthenticationPrincipal UserDetail userDetail, PostUserDTO postUser) {
        User user = userDetail.getUser();
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
            } catch (IOException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            user.setIcon(fileName);
        }
        if (!postUser.isEmptyBack()) {
            String fileName = user.getId() + formatter.format(LocalDateTime.now()) + "." + postUser.getBackExtention();
            try (InputStream inputStream = (postUser.getBack()).getInputStream();
                    OutputStream outputStream = Files.newOutputStream(Paths.get("./backs", fileName))) {
                inputStream.transferTo(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            user.setBack(fileName);
        }
        userService.saveUser(user);
    }

    @PostMapping("/tw")
    @ResponseStatus(HttpStatus.OK)
    public void postTw(@AuthenticationPrincipal UserDetail userDetail, PostTwDTO postTw) {
        try {
            eTwService.savePostTwDTO(userDetail, postTw);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deletetw")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTw(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        try {
            eTwService.deleteETw(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/follow")
    @ResponseStatus(HttpStatus.OK)
    public void postFollow(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        Optional<User> targetUser = userService.getUserById(Integer.parseInt(id));
        if (!targetUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        followService.saveFollow(userDetail.getUser(), targetUser.get());
    }

    @PostMapping("/unfollow")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFollow(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        Optional<User> targetUser = userService.getUserById(Integer.parseInt(id));
        if (!targetUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        followService.deleteByUserAndTargetUser(userDetail.getUser(), targetUser.get());
    }

    @PostMapping("/favorite")
    @ResponseStatus(HttpStatus.OK)
    public void postFavorite(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        try {
            eTwService.favoriteETw(id, (userDetail.getUser()).getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/unfavorite")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFavorite(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        try {
            eTwService.unfavoriteETw(id, (userDetail.getUser()).getId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
