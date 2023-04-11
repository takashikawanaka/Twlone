package com.twlone.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

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

    String symbol;
    Pattern pattern;

    public UserController(UserService service, TwService service2, FollowService service3, FavoriteService service4) {
        this.userService = service;
        this.twService = service2;
        this.followService = service3;
        this.favoriteService = service4;

        this.symbol = "!\"#$%&'()\\*\\+\\-\\.,\\/:;<=>?@\\[\\\\\\]^_`{|}~";
        this.pattern = Pattern.compile("(?<!#)#(([^\\s" + symbol + "]*[^\\s\\d" + symbol + "][^\\s" + symbol + "]*)+)");
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

    private List<List<String>> splitContent(Integer count, String content) {
        if (0 == count)
            return content.isEmpty() ? List.of(List.of()) : List.of(List.of("none", content));
        List<List<String>> splitList = new ArrayList<>();
        Matcher matcher = this.pattern.matcher(content);
        int i = 0;
        while (matcher.find()) {
            if (i != matcher.start())
                splitList.add(List.of("none", content.substring(i, matcher.start())));
            splitList.add(List.of("hashtag", matcher.group()));
            i = matcher.end();
        }
        if (i != content.length())
            splitList.add(List.of("none", content.substring(i, content.length())));
        return splitList;
    }

    // -- Convert TwDTO --
    private TwDTO.TwDTOBuilder getBuilder(Tw tw) {
        return TwDTO.builder()
                .id(tw.getId())
                .content(splitContent(twService.getCountRelatedTwHashTagByTw(tw), tw.getContent()))
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
        return this.getBuilder(tw)
                .reTw(this.convertTwDTO(tw.getReTw(), user))
                .replyTw(this.convertTwDTO(tw.getReplyTw(), user))
                .isFavorite(favoriteService.getBooleanByTwAndUser(tw, user) ? true : false)
                .replyTwList(twDTOList)
                .build();
    }
}
