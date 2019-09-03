package com.fsd.um.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fsd.um.entity.User;
import com.fsd.um.exception.CustomException;
import com.fsd.um.response.HttpResponse;
import com.fsd.um.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;



    
    @GetMapping("/tutorials")
    public String tutorials() {
        return "user/tutorials";
    }
    
    @PutMapping("/account")
    @ResponseBody
    public HttpResponse updateAccount(@RequestParam(name = "kaptcha", required = true) String kaptcha,
            @Valid @RequestBody User user, HttpSession session) {

        checkCaptcha(kaptcha, session);

        userService.updateUser(user);

        return HttpResponse.OK;
    }
    
    @GetMapping("/account")
    public String preUpdateAccount(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUsername(username);

        model.addAttribute("user", user);

        return "user/update_account";
    }

}