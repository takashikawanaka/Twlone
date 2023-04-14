package com.twlone.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.twlone.dto.PostTwDTO;
import com.twlone.dto.UserDTO;
import com.twlone.entity.Tw;
import com.twlone.entity.User;
import com.twlone.service.TwService;
import com.twlone.service.UserDetail;
import com.twlone.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final TwService twService;

    public UserController(UserService service, TwService service2) {
        this.userService = service;
        this.twService = service2;
    }

    @GetMapping("/{userId}")
    public String getUser(@AuthenticationPrincipal UserDetail userDetail, @PathVariable("userId") String id,
            Model model) {
        Optional<UserDTO> user = userService.getUserDTOByUserId(id);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        UserDTO userDTO = user.get();
        if (userDetail != null) {
            User loggedUser = userDetail.getUser();
            model.addAttribute("logged", loggedUser);
            model.addAttribute("postTw", new PostTwDTO());
            userDTO.setIsFollow(userService.getBooleanFollowBySourceUserAndTargetUserID(loggedUser, userDTO));
            userDTO.setTwList(twService.getTwDTOListByUserDTO(userDTO, loggedUser));
            model.addAttribute("user", userDTO);
        } else {
            userDTO.setTwList(twService.getTwDTOListByUserDTO(userDTO));
            model.addAttribute("user", userDTO);
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
            model.addAttribute("twDTO", twService.convertTwDTOReplyTw(tw.get(), userDetail.getUser()));
        } else {
            model.addAttribute("twDTO", twService.convertTwDTOReplyTw(tw.get()));
        }
        return "tw";
    }
}
