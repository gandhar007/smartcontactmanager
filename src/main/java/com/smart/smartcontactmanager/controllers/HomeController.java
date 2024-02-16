package com.smart.smartcontactmanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.smartcontactmanager.entities.User;

@Controller
public class HomeController {
    
    @RequestMapping("/")
    public String home() {
        return "homepage";
    }

    @RequestMapping("/about")
    public String about(Model m) {
        m.addAttribute("title", "Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup")
    public String signUp(Model m) {
        m.addAttribute("title", "Sign Up Page");
        m.addAttribute("user", new User());
        return "signup";
    }

    // Handler for registering user
    @RequestMapping(value = "do_register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user) {
        return "signup";
    }
}
