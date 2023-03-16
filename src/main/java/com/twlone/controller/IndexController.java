package com.twlone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.twlone.entity.Authorization;
import com.twlone.entity.User;
import com.twlone.service.AuthorizationService;
import com.twlone.service.UserService;

@Controller
public class IndexController {
    AuthorizationService authorizationService;
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public IndexController(AuthorizationService service, UserService service2) {
        this.authorizationService = service;
        this.userService = service2;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/register")
    public String postRegister(@Validated Authorization authorization, BindingResult res, Model model) {
        if (res.hasErrors()) {
            return getLogin(authorization);// forward
        }
        User user = authorization.getUser();
        user.setDeleteFlag(0);
        authorization.setUser(user);
        userService.saveUser(user);
        String passwordString = passwordEncoder.encode(authorization.getPassword());
        authorization.setPassword(passwordString);
        authorization.setUser(user);
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
}
