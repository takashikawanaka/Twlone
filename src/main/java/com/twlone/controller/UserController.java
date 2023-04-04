package com.twlone.controller;

import java.time.LocalDate;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
            // User loggedUser = userDetail.getUser();
            user.setIsFollow(followService.getBooleanByUserIdAndTargetUser(userDetail.getUser(), user));
            for (Tw tw : user.getTwList()) {
                tw.setIsFavorite(favoriteService.getBooleanByTwAndUser(tw, userDetail.getUser()));
            }
            model.addAttribute("logged", userDetail.getUser());
        }
        LocalDate date = LocalDate.now();
        for (Tw tw : user.getTwList()) {
            if (!tw.getCreatedAt().toLocalDate().isEqual(date)) {
                tw.setDayHasPassed(true);
            }
        }
        model.addAttribute("user", user);
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
        Tw tw = twService.getTwById(id);
        if (userDetail != null) {
            tw.setIsFavorite(favoriteService.getBooleanByTwAndUser(tw, userDetail.getUser()));
            model.addAttribute("logged", userDetail.getUser());
        }
        model.addAttribute("tw", tw);
        return "tw";
    }
}
