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

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.twlone.dto.PostTw;
import com.twlone.entity.Media;
import com.twlone.entity.Media.MediaType;
import com.twlone.entity.Tw;
import com.twlone.entity.User;
import com.twlone.service.FavoriteService;
import com.twlone.service.FollowService;
import com.twlone.service.MediaService;
import com.twlone.service.TwService;
import com.twlone.service.UserDetail;
import com.twlone.service.UserService;

@RestController
@RequestMapping("user")
public class UserPostController {
    UserService userService;
    TwService twService;
    FollowService followService;
    FavoriteService favoriteService;
    MediaService mediaService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public UserPostController(UserService service, TwService service2, FollowService service3, FavoriteService service4,
            MediaService service5) {
        this.userService = service;
        this.twService = service2;
        this.followService = service3;
        this.favoriteService = service4;
        this.mediaService = service5;
    }

    @PostMapping("/tw")
    @ResponseStatus(HttpStatus.OK)
    public void postTw(@AuthenticationPrincipal UserDetail userDetail, PostTw postTw) {
        if (postTw.isIllegale())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Tw tw = new Tw(userDetail.getUser());
        if (!postTw.isBlankContent())
            tw.setContent(postTw.getContent());
        postTw.getReTwID().ifPresent(id -> tw.setReTw(twService.getTwById(id)));
        postTw.getReplyTwID().ifPresent(id -> tw.setReplyTw(twService.getTwById(id)));
        postTw.getMedia().ifPresent(medias -> {
            try {
                if (4 < medias.size())
                    throw new IllegalArgumentException();
                tw.setMediaList(saveMedias(medias, tw));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
        twService.saveTw(tw);
    }

    public List<Media> saveMedias(List<MultipartFile> medias, Tw tw) throws IOException {
        List<Media> mediaList = new ArrayList<>();
        for (int i = 0; i < medias.size(); i++) {
            MultipartFile media = medias.get(i);
            String extention = media.getContentType().split("/")[1];
            MediaType type = MediaType.valueOf(extention);
            String fileName = tw.getUser().getId() + formatter.format(LocalDateTime.now()) + i + "." + extention;
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
        Tw tw = twService.getTwById(Integer.parseInt(id));
        tw.setDeleteFlag(1);
        twService.saveTw(tw);
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
