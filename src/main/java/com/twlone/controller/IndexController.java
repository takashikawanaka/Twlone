package com.twlone.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.twlone.entity.Authorization;
import com.twlone.entity.User;
import com.twlone.service.AuthorizationService;
import com.twlone.service.UserDetail;
import com.twlone.service.UserService;

@Controller
public class IndexController {
    private final AuthorizationService authorizationService;
    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public IndexController(AuthorizationService service, UserService service2) {
        this.authorizationService = service;
        this.userService = service2;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        String url = (String) session.getAttribute("referer");
        if (url != null && !url.equals("login"))
            return "redirect:" + "/" + url;
        // Will Remove
        List<User> userlist = userService.getUserList();
        model.addAttribute("userlist", userlist);
        return "index";
    }

    @PostMapping("/register")
    public String postRegister(@Validated Authorization authorization, BindingResult res,
            RedirectAttributes redirectAttributes) {
        if (res.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.authorization", res);
            redirectAttributes.addFlashAttribute("authorization", authorization);
        } else if (userService.getExistsByUserId((authorization.getUser()).getUserId())) {
            res.rejectValue("user.userId", "form.user.userId.message");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.authorization", res);
            redirectAttributes.addFlashAttribute("authorization", authorization);
        } else {
            authorization.setPassword(passwordEncoder.encode(authorization.getPassword()));
            authorizationService.saveAuthorization(authorization);
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(@AuthenticationPrincipal UserDetail userDetail, HttpServletRequest request, Model model) {
        if (userDetail != null)
            return "redirect:/";
        if (!model.containsAttribute("org.springframework.validation.BindingResult.authorization"))
            model.addAttribute("authorization", new Authorization());
        String referer = request.getHeader("referer");
        if (referer != null) {
            String[] urls = (referer).split("/");
            if (3 < urls.length)
                request.getSession()
                        .setAttribute("referer", String.join("/", Arrays.copyOfRange(urls, 3, urls.length)));
        }
        return "login";
    }

    @PostMapping("/logout")
    public String postLogout() {
        return "redirect:/login";
    }
}
