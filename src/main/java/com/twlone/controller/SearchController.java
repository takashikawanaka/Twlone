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
import com.twlone.entity.Media.MediaType;
import com.twlone.service.TwService;
import com.twlone.service.UserDetail;

@Controller
public class SearchController {
    private final TwService twService;

    public SearchController(TwService service) {
        this.twService = service;
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

    public void loadFile(String filename1, String filename2, HttpServletResponse response)
            throws FileNotFoundException, IOException {
        String extention = filename2.substring(filename2.lastIndexOf('.') + 1);
        String mediaType = (MediaType.valueOf(extention)).getContentType();
        response.setContentType(mediaType);
        Path path = Paths.get(filename1, filename2);
        if (!Files.exists(path))
            throw new FileNotFoundException();
        try (InputStream inputStream = Files.newInputStream(path)) {
            inputStream.transferTo(response.getOutputStream());
        }
    }
}
