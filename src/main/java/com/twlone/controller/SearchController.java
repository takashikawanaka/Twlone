package com.twlone.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.twlone.entity.HashTag;
import com.twlone.entity.Media;
import com.twlone.service.HashTagService;
import com.twlone.service.MediaService;
import com.twlone.service.TwService;
import com.twlone.service.UserDetail;

@Controller
public class SearchController {
    private final TwService twService;
    private final MediaService mediaService;
    private final HashTagService hashtagService;

    public SearchController(TwService service, MediaService service2, HashTagService service3) {
        this.twService = service;
        this.mediaService = service2;
        this.hashtagService = service3;
    }

    @GetMapping("/search")
    public String index(@AuthenticationPrincipal UserDetail userDetail,
            @RequestParam(name = "word", required = false) String word, Model model) {
        if (word == null)
            return "search";
        if (word.startsWith("#")) {
            Optional<HashTag> hashtag = hashtagService.getHashTagByName(word.substring(1));
            if (hashtag.isPresent()) {
                model.addAttribute("twList", (userDetail == null) ? twService.getTwDTOListByHashTag(hashtag.get())
                        : twService.getTwDTOListByHashTag(hashtag.get(), userDetail.getUser()));
            }
        } else {
            model.addAttribute("twList", (userDetail == null) ? twService.getTwDTOListByContent(word)
                    : twService.getTwDTOListByContent(word, userDetail.getUser()));
        }
        if (userDetail != null) {
            model.addAttribute("logged", userDetail.getUser());
        }
        return "search";
    }

    @GetMapping("/media/{filename}")
    public void takeFile(@PathVariable String filename, HttpServletResponse response) {
        Optional<Media> media = mediaService.getMediaByPath(filename);
        if (!media.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try (InputStream inputStream = Files.newInputStream(Paths.get("./medias", filename))) {
            response.setContentType(media.get()
                    .getType()
                    .getContentType());
            inputStream.transferTo(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
