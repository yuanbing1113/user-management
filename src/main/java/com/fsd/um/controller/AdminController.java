package com.fsd.um.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fsd.um.entity.User;
import com.fsd.um.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;



    
    @GetMapping("/admin")
    public String tutorials() {
        return "admin/admin";
    }
    
    @GetMapping("/userlist")
    public String users(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/userlist";
    }

}