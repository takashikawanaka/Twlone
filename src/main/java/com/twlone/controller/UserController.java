package com.twlone.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.twlone.dto.MiniUserDTO;
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
    UserService userService;
    TwService twService;
    FollowService followService;
    FavoriteService favoriteService;

    public UserController(UserService service, TwService service2, FollowService service3, FavoriteService service4) {
        this.userService = service;
        this.twService = service2;
        this.followService = service3;
        this.favoriteService = service4;
    }

    @GetMapping("/{userId}")
    public String getUser(@AuthenticationPrincipal UserDetail userDetail, @PathVariable("userId") String id,
            Model model) {
        User user = userService.getUserByUserId(id);
        if (userDetail != null) {
            model.addAttribute("logged", userDetail.getUser());
            model.addAttribute("postTw", new PostTwDTO());
            model.addAttribute("user", this.convertFullUserDTO(userDetail.getUser(), user));
        } else {
            model.addAttribute("user", this.convertFullUserDTO(user));
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
        if (userDetail != null) {
            model.addAttribute("logged", userDetail.getUser());
            model.addAttribute("postTw", new PostTwDTO());
            model.addAttribute("twDTO", this.convertTwDTOReplyTw(twService.getTwById(id), userDetail.getUser()));
        } else {
            model.addAttribute("twDTO", this.convertTwDTOReplyTw(twService.getTwById(id)));
        }
        return "tw";
    }

    // -- Convert MiniUserDTO --
    public MiniUserDTO convertMiniUserDTO(User user) {
        return MiniUserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .icon(user.getIcon())
                .build();
    }

    // -- Convert UserDTO --
    public UserDTO.UserDTOBuilder getFullUserDTOBuilder(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .description(user.getDescription())
                .icon(user.getIcon())
                .back(user.getBack())
                .followingListSize(userService.getCountFollowingByUser(user))
                .followerListSize(userService.getCountFollowerByUser(user));
    }

    public UserDTO convertFullUserDTO(User user) {
        return this.getFullUserDTOBuilder(user)
                .twList(user.getTwList()
                        .stream()
                        .map(tw -> this.convertTwDTO(tw))
                        .collect(Collectors.toList()))
                .build();
    }

    public UserDTO convertFullUserDTO(User sourceUser, User targetUser) {
        return this.getFullUserDTOBuilder(targetUser)
                .twList(targetUser.getTwList()
                        .stream()
                        .map(tw -> this.convertTwDTO(tw, sourceUser))
                        .collect(Collectors.toList()))
                .isFollow(followService.getBooleanByUserIdAndTargetUser(sourceUser, targetUser))
                .build();
    }

    // -- Convert TwDTO --
    private TwDTO.TwDTOBuilder getBuilder(Tw tw) {
        return TwDTO.builder()
                .id(tw.getId())
                .content(tw.getContent())
                .user(this.convertMiniUserDTO(tw.getUser()))
                .createdAt(tw.getCreatedAt())
                .reTwListSize(twService.getCountReTwByTw(tw))
                .replyTwListSize(twService.getCountReplyTwByTw(tw))
                .favoriteListSize(twService.getCountFavoriteByTw(tw))
                .mediaList(tw.getMediaList())
                .dayHasPassed(!tw.getCreatedAt()
                        .toLocalDate()
                        .isEqual(LocalDate.now()));
    }

    // Recursive function
    public TwDTO convertTwDTO(Tw tw) {
        if (tw == null)
            return null;
        return this.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw()))
                .build();
    }

    public TwDTO convertTwDTO(Tw tw, User user) {
        if (tw == null)
            return null;
        return this.getBuilder(tw)
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
        return this.getBuilder(tw)
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
        return getBuilder(tw).reTw(this.convertTwDTO(tw.getReTw(), user))
                .replyTw(this.convertTwDTO(tw.getReplyTw(), user))
                .isFavorite(favoriteService.getBooleanByTwAndUser(tw, user) ? true : false)
                .replyTwList(twDTOList)
                .build();
    }
}
