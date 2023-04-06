package com.twlone.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.twlone.dto.PostTwDTO;
import com.twlone.dto.TwDTO;
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
        List<TwDTO> twDTOList = new ArrayList<TwDTO>();
        if (userDetail != null) {
            user.setIsFollow(followService.getBooleanByUserIdAndTargetUser(userDetail.getUser(), user));
            for (Tw tw : user.getTwList()) {
                twDTOList.add(this.convertFromTw(tw, userDetail.getUser()));
            }
            model.addAttribute("logged", userDetail.getUser());
            model.addAttribute("postTw", new PostTwDTO());
        } else {
            for (Tw tw : user.getTwList()) {
                twDTOList.add(this.convertFromTw(tw));
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("twDTOList", twDTOList);
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
        TwDTO twDTO;
        if (userDetail != null) {
            twDTO = convertFromTwIncludeReplyTwList(twService.getTwById(id), userDetail.getUser());
            model.addAttribute("logged", userDetail.getUser());
            model.addAttribute("postTw", new PostTwDTO());
        } else {
            twDTO = convertFromTwIncludeReplyTwList(twService.getTwById(id));
        }
        model.addAttribute("twDTO", twDTO);
        return "tw";
    }

    public Boolean isEqualDayCreatedAt(LocalDateTime createdAt) {
        return createdAt.toLocalDate()
                .isEqual(LocalDate.now());
    }

    public TwDTO convertFromTw(Tw tw) {
        if (tw == null)
            return null;
        TwDTO twDTO = TwDTO.builder()
                .id(tw.getId())
                .content(tw.getContent())
                .user(tw.getUser())
                .reTw(this.convertFromTw(tw.getReTw()))
                .replyTw(this.convertFromTw(tw.getReplyTw()))
                .createdAt(tw.getCreatedAt())
                .reTwListSize(twService.getCountReTwByTw(tw))
                .replyTwListSize(twService.getCountReplyTwByTw(tw))
                .favoriteListSize(twService.getCountFavoriteByTw(tw))
                .mediaList(tw.getMediaList())
                .dayHasPassed(!tw.getCreatedAt()
                        .toLocalDate()
                        .isEqual(LocalDate.now()))
                .build();
        return twDTO;
    }

    public TwDTO convertFromTw(Tw tw, User user) {
        if (tw == null)
            return null;
        TwDTO twDTO = TwDTO.builder()
                .id(tw.getId())
                .content(tw.getContent())
                .user(tw.getUser())
                .reTw(this.convertFromTw(tw.getReTw(), user))
                .replyTw(this.convertFromTw(tw.getReplyTw(), user))
                .createdAt(tw.getCreatedAt())
                .reTwListSize(twService.getCountReTwByTw(tw))
                .replyTwListSize(twService.getCountReplyTwByTw(tw))
                .favoriteListSize(twService.getCountFavoriteByTw(tw))
                .mediaList(tw.getMediaList())
                .isFavorite(favoriteService.getBooleanByTwAndUser(tw, user) ? true : false)
                .dayHasPassed(!tw.getCreatedAt()
                        .toLocalDate()
                        .isEqual(LocalDate.now()))
                .build();
        return twDTO;
    }

    public TwDTO convertFromTwIncludeReplyTwList(Tw tw) {
        List<TwDTO> twDTOList = new ArrayList<TwDTO>();
        for (Tw reply : tw.getReplyTwList()) {
            twDTOList.add(this.convertFromTw(reply));
        }
        TwDTO twDTO = TwDTO.builder()
                .id(tw.getId())
                .content(tw.getContent())
                .user(tw.getUser())
                .reTw(this.convertFromTw(tw.getReTw()))
                .replyTw(this.convertFromTw(tw.getReplyTw()))
                .createdAt(tw.getCreatedAt())
                .reTwListSize(twService.getCountReTwByTw(tw))
                .replyTwListSize(twService.getCountReplyTwByTw(tw))
                .replyTwList(twDTOList)
                .favoriteListSize(twService.getCountFavoriteByTw(tw))
                .mediaList(tw.getMediaList())
                .dayHasPassed(!tw.getCreatedAt()
                        .toLocalDate()
                        .isEqual(LocalDate.now()))
                .build();
        return twDTO;
    }

    public TwDTO convertFromTwIncludeReplyTwList(Tw tw, User user) {
        List<TwDTO> twDTOList = new ArrayList<TwDTO>();
        for (Tw reply : tw.getReplyTwList()) {
            twDTOList.add(this.convertFromTw(reply, user));
        }
        TwDTO twDTO = TwDTO.builder()
                .id(tw.getId())
                .content(tw.getContent())
                .user(tw.getUser())
                .reTw(this.convertFromTw(tw.getReTw(), user))
                .replyTw(this.convertFromTw(tw.getReplyTw(), user))
                .createdAt(tw.getCreatedAt())
                .reTwListSize(twService.getCountReTwByTw(tw))
                .replyTwListSize(twService.getCountReplyTwByTw(tw))
                .replyTwList(twDTOList)
                .favoriteListSize(twService.getCountFavoriteByTw(tw))
                .mediaList(tw.getMediaList())
                .isFavorite(favoriteService.getBooleanByTwAndUser(tw, user) ? true : false)
                .dayHasPassed(!tw.getCreatedAt()
                        .toLocalDate()
                        .isEqual(LocalDate.now()))
                .build();
        return twDTO;
    }
}
