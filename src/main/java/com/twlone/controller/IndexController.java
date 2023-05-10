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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.twlone.dto.MiniUserDTO;
import com.twlone.dto.PostTwDTO;
import com.twlone.entity.Authorization;
import com.twlone.entity.User;
import com.twlone.service.AuthorizationService;
import com.twlone.service.ETwService;
import com.twlone.service.UserDetail;
import com.twlone.service.UserService;

@Controller
public class IndexController {
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final ETwService eTwService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public IndexController(AuthorizationService service, UserService service2, ETwService service3) {
        this.authorizationService = service;
        this.userService = service2;
        this.eTwService = service3;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserDetail userDetail, HttpSession session, Model model,
            @RequestParam(name = "redirect", required = false, defaultValue = "true") Boolean redirect) {
        // Redirect
        String url = (String) session.getAttribute("loginReferer");
        if (redirect && url != null && !url.equals("login")) {
            session.removeAttribute("loginReferer");
            return "redirect:" + "/" + url;
        }
        // Show HomeTimeLine
        User logged = userDetail.getUser();
        model.addAttribute("logged", logged);
        model.addAttribute("postTw", new PostTwDTO());
        List<Integer> userList = userService.getFollowingIdListByUser(logged);
        model.addAttribute("twList", eTwService.getTimeLineByUserIdList(userList, logged.getId()));

        // Random Recommend User
        List<MiniUserDTO> userDTOList = userService.getUserListByRandomId();
        for (MiniUserDTO userDTO : userDTOList) {
            System.out.println(userDTO.getUserId());
        }
        // Will Remove
        List<User> userlist = userService.getUserList();
        model.addAttribute("userlist", userlist);

        return "index";
    }

    @PostMapping("/register")
    public String postRegister(@Validated Authorization authorization, BindingResult res,
            RedirectAttributes redirectAttributes) {
        if (res.hasErrors()) { // ValidatedError
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.authorization", res);
            redirectAttributes.addFlashAttribute("authorization", authorization);
        } else if (userService.getExistsByUserId((authorization.getUser()).getUserId())) { // Already exist Error
            res.rejectValue("user.userId", "form.user.userId.message");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.authorization", res);
            redirectAttributes.addFlashAttribute("authorization", authorization);
        } else { // Success Register
            authorization.setPassword(passwordEncoder.encode(authorization.getPassword()));
            authorizationService.saveAuthorization(authorization);
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(@AuthenticationPrincipal UserDetail userDetail, HttpServletRequest request, Model model) {
        if (userDetail != null) // Check already logged in
            return "redirect:/";
        if (!model.containsAttribute("org.springframework.validation.BindingResult.authorization")) // Add Error
            model.addAttribute("authorization", new Authorization());
        String referer = request.getHeader("referer");
        if (referer != null) { // Redirect to previously visited site after successful login
            String[] urls = (referer).split("/");
            if (3 < urls.length)
                request.getSession()
                        .setAttribute("loginReferer", String.join("/", Arrays.copyOfRange(urls, 3, urls.length)));
        }
        return "login";
    }

    @PostMapping("/logout")
    public String postLogout() {
        return "redirect:/login";
    }
}
