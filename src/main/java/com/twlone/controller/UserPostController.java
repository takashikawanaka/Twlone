package com.twlone.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.twlone.entity.Media;
import com.twlone.entity.Media.MediaType;
import com.twlone.entity.Tw;
import com.twlone.service.FavoriteService;
import com.twlone.service.FollowService;
import com.twlone.service.MediaService;
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
    public void postTw(@AuthenticationPrincipal UserDetail userDetail,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "reTw", required = false) Optional<String> reTw,
            @RequestParam(name = "replyTw", required = false) Optional<String> replyTw,
            @RequestPart(name = "file", required = false) Optional<List<MultipartFile>> medias) {

        Boolean isContent = false;
        Tw tw = new Tw(userDetail.getUser());
        if (!content.isEmpty()) {
            tw.setContent(content);
            isContent = true;
        }
        if (reTw.isPresent() && !reTw.map(String::isEmpty).orElse(false)) {
            tw.setReTw(twService.getTwById(Integer.parseInt(reTw.get())));
            isContent = true;
        }
        if (replyTw.isPresent() && !replyTw.map(String::isEmpty).orElse(false)) {
            tw.setReplyTw(twService.getTwById(Integer.parseInt(replyTw.get())));
        }
        List<Media> mediaList = null;
        if (medias.isPresent()) {
            if (4 < medias.get().size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            isContent = true;
            try {
                mediaList = saveMedias(medias.get(), tw);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        if (isContent) {
            twService.saveTw(tw);
            if (mediaList != null)
                mediaList.forEach(media -> mediaService.saveMedia(media));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Media> saveMedias(List<MultipartFile> medias, Tw tw) {
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
            } catch (Exception e) {
                e.printStackTrace();
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
