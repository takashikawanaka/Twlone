package com.twlone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.twlone.entity.Favorite;
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
public class UserPostController {
    UserService userService;
    TwService twService;
    FollowService followService;
    FavoriteService favoriteService;

    public UserPostController(UserService service, TwService service2, FollowService service3,
            FavoriteService service4) {
        this.userService = service;
        this.twService = service2;
        this.followService = service3;
        this.favoriteService = service4;
    }

    @PostMapping("/tw")
    @ResponseStatus(HttpStatus.OK)
    public void postTw(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String content) {
        twService.saveTw(userDetail.getUser(), content);
    }

    @PostMapping("/follow")
    @ResponseStatus(HttpStatus.OK)
    public void postFollow(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        followService.saveFollow(userDetail.getUser(), userService.getUserById(Integer.parseInt(id)));
    }

    @PostMapping("/unfollow")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFollow(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        followService.deleteByUserAndTargetUser(userDetail.getUser(), userService.getUserById(Integer.parseInt(id)));
    }

    @PostMapping("/favorite")
    @ResponseStatus(HttpStatus.OK)
    public void postFavorite(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        favoriteService.saveFavorite(twService.getTwById(Integer.parseInt(id)), userDetail.getUser());
    }

    @PostMapping("/unfavorite")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFavorite(@AuthenticationPrincipal UserDetail user, @RequestParam String id) {
        favoriteService.deleteByTwAndUser(twService.getTwById(Integer.parseInt(id)), user.getUser());
    }
}
