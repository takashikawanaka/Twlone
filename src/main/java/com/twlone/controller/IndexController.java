package com.twlone.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.twlone.entity.Authorization;
import com.twlone.entity.Media;
import com.twlone.entity.User;
import com.twlone.service.AuthorizationService;
import com.twlone.service.MediaService;
import com.twlone.service.UserService;

@Controller
public class IndexController {
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final MediaService mediaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public IndexController(AuthorizationService service, UserService service2, MediaService service3) {
        this.authorizationService = service;
        this.userService = service2;
        this.mediaService = service3;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Will Remove
        List<User> userlist = userService.getUserList();
        model.addAttribute("userlist", userlist);
        return "index";
    }

    @PostMapping("/register")
    public String postRegister(@Validated Authorization authorization, BindingResult res, Model model) {
        if (res.hasErrors()) {
            return getLogin(authorization);// forward??
        }
        User user = authorization.getUser();
        userService.saveUser(user);
        String passwordString = passwordEncoder.encode(authorization.getPassword());
        authorization.setPassword(passwordString);
        authorizationService.saveAuthorization(authorization);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute Authorization authorizationx) {
        return "login";
    }

    @PostMapping("/logout")
    public String postLogout() {
        return "redirect:/login";
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
