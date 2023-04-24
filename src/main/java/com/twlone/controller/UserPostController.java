package com.twlone.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.twlone.dto.PostTwDTO;
import com.twlone.dto.PostUserDTO;
import com.twlone.entity.HashTag;
import com.twlone.entity.Media;
import com.twlone.entity.Media.MediaType;
import com.twlone.entity.RelatedTwHashTag;
import com.twlone.entity.Tw;
import com.twlone.entity.User;
import com.twlone.service.FavoriteService;
import com.twlone.service.FollowService;
import com.twlone.service.HashTagService;
import com.twlone.service.TwService;
import com.twlone.service.UserDetail;
import com.twlone.service.UserService;

@RestController
@RequestMapping("user")
public class UserPostController {
    private final UserService userService;
    private final TwService twService;
    private final FollowService followService;
    private final FavoriteService favoriteService;
    private final HashTagService hashtagService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public UserPostController(UserService service, TwService service2, FollowService service3, FavoriteService service4,
            HashTagService service5) {
        this.userService = service;
        this.twService = service2;
        this.followService = service3;
        this.favoriteService = service4;
        this.hashtagService = service5;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void postUser(@AuthenticationPrincipal UserDetail userDetail, PostUserDTO postUser) {
        User user = userDetail.getUser();
        if (!postUser.isBlankName())
            user.setName(postUser.getName());
        if (!postUser.isBlankUserId())
            user.setUserId((postUser.getUserId()));
        if (!postUser.isBlankDescription())
            user.setDescription(postUser.getDescription());
        userService.saveUser(user);
    }

    @PostMapping("/tw")
    @ResponseStatus(HttpStatus.OK)
    public void postTw(@AuthenticationPrincipal UserDetail userDetail, PostTwDTO postTw) {
        if (postTw.isIllegale())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Tw tw = new Tw(userDetail.getUser());
        if (!postTw.isBlankContent()) {
            if (postTw.existsHashTag()) {
                List<RelatedTwHashTag> relatedTwHashTagList = new ArrayList<>();
                for (String name : postTw.getHashtag()) {
                    HashTag hashtag = hashtagService.getHashTagByName(name)
                            .orElseGet(() -> hashtagService.saveHashTag(new HashTag(name)));
                    relatedTwHashTagList.add(new RelatedTwHashTag(tw, hashtag));
                }
                tw.setHashtagList(relatedTwHashTagList);
            }
            tw.setContent(postTw.getContent());
        }
        if (postTw.existsReTwId()) {
            Optional<Tw> retw = twService.getTwById(postTw.getReTwID());
            if (!retw.isPresent())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            tw.setReTw(retw.get());
        }
        if (postTw.existsReplyTwId()) {
            Optional<Tw> replytw = twService.getTwById(postTw.getReplyTwID());
            if (!replytw.isPresent())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            tw.setReplyTw(replytw.get());
        }
        if (postTw.existsMedia()) {
            try {
                tw.setMediaList(saveMedias(postTw.getMedia(), tw));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        twService.saveTw(tw);
    }

    public List<Media> saveMedias(List<MultipartFile> medias, Tw tw) throws IOException {
        List<Media> mediaList = new ArrayList<>();
        for (int i = 0; i < medias.size(); i++) {
            MultipartFile media = medias.get(i);
            String extention = (media.getContentType()).split("/")[1];
            MediaType type = MediaType.valueOf(extention);
            String fileName = (tw.getUser()).getId() + formatter.format(LocalDateTime.now()) + i + "." + extention;
            Path filePath = Paths.get("./medias", fileName);
            try (InputStream inputStream = media.getInputStream();
                    OutputStream outputStream = Files.newOutputStream(filePath)) {
                inputStream.transferTo(outputStream);
            }
            mediaList.add(new Media(tw, type, fileName));
        }
        return mediaList;
    }

    @PostMapping("/deletetw")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTw(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        Optional<Tw> tw = twService.getTwById(Integer.parseInt(id));
        if (!tw.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        (tw.get()).setDeleteFlag(1);
        twService.saveTw(tw.get());
    }

    @PostMapping("/follow")
    @ResponseStatus(HttpStatus.OK)
    public void postFollow(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        Optional<User> targetUser = userService.getUserById(Integer.parseInt(id));
        if (!targetUser.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        followService.saveFollow(userDetail.getUser(), targetUser.get());
    }

    @PostMapping("/unfollow")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFollow(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        Optional<User> targetUser = userService.getUserById(Integer.parseInt(id));
        if (!targetUser.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        followService.deleteByUserAndTargetUser(userDetail.getUser(), targetUser.get());
    }

    @PostMapping("/favorite")
    @ResponseStatus(HttpStatus.OK)
    public void postFavorite(@AuthenticationPrincipal UserDetail userDetail, @RequestParam String id) {
        Optional<Tw> tw = twService.getTwById(Integer.parseInt(id));
        if (!tw.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        favoriteService.saveFavorite(tw.get(), userDetail.getUser());
    }

    @PostMapping("/unfavorite")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFavorite(@AuthenticationPrincipal UserDetail user, @RequestParam String id) {
        Optional<Tw> tw = twService.getTwById(Integer.parseInt(id));
        if (!tw.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        favoriteService.deleteByTwAndUser(tw.get(), user.getUser());
    }
}
