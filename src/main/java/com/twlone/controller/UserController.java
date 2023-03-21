package com.twlone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.twlone.entity.Follow;
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
    public String getUser(@PathVariable("userId") String id, Model model) {
        User user = userService.getUserByUserId(id);
        model.addAttribute("user", user);
        List<Tw> twlist = twService.getTwListByUser(user);
        model.addAttribute("twlist", twlist);
        long following = followService.getFollowingCountByUserId(user);
        model.addAttribute("following", following);
        long follower = followService.getFollowerCountByTargetUserId(user);
        model.addAttribute("follower", follower);
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
    public String getTweet(@PathVariable("") Integer id) {
        return "";
    }

}
