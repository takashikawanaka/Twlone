package com.twlone.controller;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.twlone.dto.PostTwDTO;
import com.twlone.dto.PostUserDTO;
import com.twlone.dto.UserDTO;
import com.twlone.entity.User;
import com.twlone.service.ETwService;
import com.twlone.service.UserDetail;
import com.twlone.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final ETwService eTwService;

    public UserController(UserService service, ETwService service2) {
        this.userService = service;
        this.eTwService = service2;
    }

    @GetMapping("/")
    @ResponseBody
    public Boolean checkDuplication(@RequestParam String userId) {
        return userService.getExistsByUserId(userId);
    }

    @GetMapping("/{userId}")
    public String getUser(@AuthenticationPrincipal UserDetail userDetail, @PathVariable("userId") String id,
            Model model) {
        Optional<UserDTO> user = userService.getUserDTOByUserId(id);
        if (!user.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        UserDTO userDTO = user.get();
        if (userDetail != null) {
            User loggedUser = userDetail.getUser();
            model.addAttribute("logged", loggedUser);
            model.addAttribute("postTw", new PostTwDTO());
            if (!(loggedUser.getUserId()).equals(userDTO.getUserId())) {
                userDTO.setIsFollow(userService.getBooleanFollowBySourceUserAndTargetUserID(loggedUser, userDTO));
            } else {
                model.addAttribute("postUser", new PostUserDTO(loggedUser.getId()));
            }
            userDTO.setTwList(eTwService.getTwDTOListByUserIdLoggedIn(userDTO.getId(), loggedUser.getId()));
            model.addAttribute("user", userDTO);
        } else {
            userDTO.setTwList(eTwService.getTwDTOListByUserId(userDTO.getId()));
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

    @GetMapping("/{userId}/status/{twId}")
    public String getTweet(@AuthenticationPrincipal UserDetail userDetail, @PathVariable("twId") String id,
            Model model) {
        if (userDetail != null) {
            model.addAttribute("logged", userDetail.getUser());
            model.addAttribute("postTw", new PostTwDTO());
            try {
                model.addAttribute("twDTO", eTwService.getTwDTOWithReplyTwDTOById(id, (userDetail.getUser()).getId()));
            } catch (EmptyResultDataAccessException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else {
            try {
                model.addAttribute("twDTO", eTwService.getTwDTOWithReplyTwDTOById(id));
            } catch (Exception e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        return "tw";
    }
}
