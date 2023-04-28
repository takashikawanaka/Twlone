package com.twlone.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

import com.twlone.dto.PostTwDTO;
import com.twlone.entity.Media;
import com.twlone.service.MediaService;
import com.twlone.service.TwService;
import com.twlone.service.UserDetail;

@Controller
public class SearchController {
    private final TwService twService;
    private final MediaService mediaService;

    public SearchController(TwService service, MediaService service2) {
        this.twService = service;
        this.mediaService = service2;
    }

    @GetMapping("/search")
    public String index(@AuthenticationPrincipal UserDetail userDetail,
            @RequestParam(name = "word", required = false) String word, Model model) {
        if (userDetail != null) {
            model.addAttribute("logged", userDetail.getUser());
            model.addAttribute("postTw", new PostTwDTO());
            if (word != null)
                model.addAttribute("twList", twService.getTwDTOListByWord(word, userDetail.getUser()));
        } else {
            if (word != null)
                model.addAttribute("twList", twService.getTwDTOListByWord(word));
        }
        return "search";
    }

    @GetMapping("/media/{filename}")
    public void takeMedia(@PathVariable String filename, HttpServletResponse response) {
        Optional<Media> media = mediaService.getMediaByPath(filename);
        Path path = Paths.get("./medias", filename);
        if (!media.isPresent() || !Files.exists(path))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        response.setContentType(media.get()
                .getType()
                .getContentType());
        this.loadFile(path, response);
    }

    @GetMapping("/icon/{filename}")
    public void takeIcon(@PathVariable String filename, HttpServletResponse response) {
        Path path = Paths.get("./icons", filename);
        if (!Files.exists(path))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        response.setContentType("image/png");
        this.loadFile(path, response);
    }

    @GetMapping("/background/{filename}")
    public void takeBackGround(@PathVariable String filename, HttpServletResponse response) {
    }

    public void loadFile(Path path, HttpServletResponse response) {
        try (InputStream inputStream = Files.newInputStream(path)) {
            inputStream.transferTo(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
