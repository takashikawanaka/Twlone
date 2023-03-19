package com.twlone.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.twlone.entity.Tw;
import com.twlone.entity.User;
import com.twlone.service.FavoriteService;
import com.twlone.service.FollowService;
import com.twlone.service.TwService;
import com.twlone.service.UserService;

@Controller
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

    @GetMapping("/user/{userId}")
    public String getUser(@PathVariable("userId") String id, Model model) {
        User user = userService.getUserByUserId(id);
        model.addAttribute("user", user);
        List<Tw> twlist = twService.getTwListByUser(user);
        model.addAttribute("twlist", twlist);
        System.out.println(twlist);
        return "user";
    }

    @GetMapping("/user/{userId}/following")
    public String getFollowing(@PathVariable("userId") Integer id) {
        return "";
    }

    @GetMapping("/user/{userId}/followers")
    public String getFollowers(@PathVariable("userId") Integer id) {
        return "";
    }

    @GetMapping("/user/{userId}/favorites")
    public String getFavorites(@PathVariable("userId") Integer id) {
        return "";
    }

    @GetMapping("/user/{userId}/status/{tweetId}")
    public String getTweet(@PathVariable("") Integer id) {
        return "";
    }

}
