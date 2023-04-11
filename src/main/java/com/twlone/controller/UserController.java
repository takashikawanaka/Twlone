package com.twlone.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.twlone.dto.PostTwDTO;
import com.twlone.dto.TwDTO;
import com.twlone.dto.UserDTO;
import com.twlone.entity.Tw;
import com.twlone.entity.User;
import com.twlone.service.FavoriteService;
import com.twlone.service.FollowService;
import com.twlone.service.TwService;
import com.twlone.service.UserDetail;
import com.twlone.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final TwService twService;
    private final FollowService followService;
    private final FavoriteService favoriteService;

    public UserController(UserService service, TwService service2, FollowService service3, FavoriteService service4) {
        this.userService = service;
        this.twService = service2;
        this.followService = service3;
        this.favoriteService = service4;
    }

    @GetMapping("/{userId}")
    public String getUser(@AuthenticationPrincipal UserDetail userDetail, @PathVariable("userId") String id,
            Model model) {
        Optional<User> user = userService.getUserByUserId(id);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (userDetail != null) {
            model.addAttribute("logged", userDetail.getUser());
            model.addAttribute("postTw", new PostTwDTO());
            model.addAttribute("user", this.convertFullUserDTO(userDetail.getUser(), user.get()));
        } else {
            model.addAttribute("user", this.convertFullUserDTO(user.get()));
        }
        return "user";
    }

    @GetMapping("/{userId}/following")
    public String getFollowing(@PathVariable("userId") Integer id) {
        return "";
    }

    @GetMapping("/{userId}/followers")
    public String getFollowers(@PathVariable("userId") Integer id) {
        return "";
    }

    @GetMapping("/{userId}/favorites")
    public String getFavorites(@PathVariable("userId") Integer id) {
        return "";
    }

    @GetMapping("/{userId}/status/{tweetId}")
    public String getTweet(@AuthenticationPrincipal UserDetail userDetail, @PathVariable("tweetId") Integer id,
            Model model) {
        Optional<Tw> tw = twService.getTwById(id);
        if (!tw.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (userDetail != null) {
            model.addAttribute("logged", userDetail.getUser());
            model.addAttribute("postTw", new PostTwDTO());
            model.addAttribute("twDTO", this.convertTwDTOReplyTw(tw.get(), userDetail.getUser()));
        } else {
            model.addAttribute("twDTO", this.convertTwDTOReplyTw(tw.get()));
        }
        return "tw";
    }

    // Get UserDTO
    public UserDTO convertFullUserDTO(User user) {
        return userService.getFullUserDTOBuilder(user)
                .twList(user.getTwList()
                        .stream()
                        .map(tw -> this.convertTwDTO(tw))
                        .collect(Collectors.toList()))
                .build();
    }

    public UserDTO convertFullUserDTO(User sourceUser, User targetUser) {
        return userService.getFullUserDTOBuilder(targetUser)
                .twList(targetUser.getTwList()
                        .stream()
                        .map(tw -> this.convertTwDTO(tw, sourceUser))
                        .collect(Collectors.toList()))
                .isFollow(followService.getBooleanByUserIdAndTargetUser(sourceUser, targetUser))
                .build();
    }

    // Get TwDTO
    public TwDTO convertTwDTO(Tw tw) {
        if (tw == null)
            return null;
        return twService.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw()))
                .build();
    }

    public TwDTO convertTwDTO(Tw tw, User user) {
        if (tw == null)
            return null;
        return twService.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw(), user))
                .isFavorite(favoriteService.getBooleanByTwAndUser(tw, user) ? true : false)
                .build();
    }

    // Get TwDTO with ReplyTw
    public TwDTO convertTwDTOReplyTw(Tw tw) {
        List<TwDTO> twDTOList = tw.getReplyTwList()
                .stream()
                .map(reply -> this.convertTwDTO(reply))
                .collect(Collectors.toList());
        return twService.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw()))
                .replyTw(this.convertTwDTO(tw.getReplyTw()))
                .replyTwList(twDTOList)
                .build();
    }

    public TwDTO convertTwDTOReplyTw(Tw tw, User user) {
        List<TwDTO> twDTOList = tw.getReplyTwList()
                .stream()
                .map(reply -> this.convertTwDTO(reply, user))
                .collect(Collectors.toList());
        return twService.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw(), user))
                .replyTw(this.convertTwDTO(tw.getReplyTw(), user))
                .isFavorite(favoriteService.getBooleanByTwAndUser(tw, user) ? true : false)
                .replyTwList(twDTOList)
                .build();
    }
}
