package com.twlone.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import com.twlone.entity.Url;
import com.twlone.service.ETwService;
import com.twlone.service.ETwService.MediaType;
import com.twlone.service.UrlService;
import com.twlone.service.UserDetail;

@Controller
public class SearchController {
    private final ETwService eTwService;
    private final UrlService urlService;

    public SearchController(ETwService service, UrlService service2) {
        this.eTwService = service;
        this.urlService = service2;
    }

    // Search Tw
    @GetMapping("/search")
    public String search(@AuthenticationPrincipal UserDetail userDetail,
            @RequestParam(name = "word", required = false) String word, Model model) {
        if (userDetail != null) {
            model.addAttribute("logged", userDetail.getUser());
            model.addAttribute("postTw", new PostTwDTO());
            if (word != null)
                model.addAttribute("twList", eTwService.searchTwDTOListByWord(word, (userDetail.getUser()).getId()));
        } else {
            if (word != null)
                model.addAttribute("twList", eTwService.searchTwDTOListByWord(word));
        }
        return "search";
    }

    // Show Media in Content
    @GetMapping("/media/{filename}")
    public void takeMedia(@PathVariable String filename, HttpServletResponse response) {
        try {
            this.loadFile("./medias", filename, response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Show User Icon
    @GetMapping("/icon/{filename}")
    public void takeIcon(@PathVariable String filename, HttpServletResponse response) {
        try {
            this.loadFile("./icons", filename, response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Show User Back Image
    @GetMapping("/back/{filename}")
    public void takeBackGround(@PathVariable String filename, HttpServletResponse response) {
        try {
            this.loadFile("./backs", filename, response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Load Image File
    private void loadFile(String filename1, String filename2, HttpServletResponse response)
            throws FileNotFoundException, IOException {
        String extention = filename2.substring(filename2.lastIndexOf('.') + 1);
        response.setContentType((MediaType.valueOf(extention)).getContentType());
        Path path = Paths.get(filename1, filename2);
        if (!Files.exists(path))
            throw new FileNotFoundException();
        try (InputStream inputStream = Files.newInputStream(path)) {
            inputStream.transferTo(response.getOutputStream());
        }
    }

    // Redirect short URL
    @GetMapping("/link/{twId}")
    public void searchLink(@PathVariable String twId, HttpServletResponse response) {
        try {
            Url url = urlService.getUrlById(twId);
            response.sendRedirect(url.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
