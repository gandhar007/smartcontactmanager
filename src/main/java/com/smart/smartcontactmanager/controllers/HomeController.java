package com.smart.smartcontactmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.helper.Message;
import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    
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
    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, 
                            Model model, HttpSession session) {
        try {
            if(!agreement) {
                System.out.println("You haven't agreed to the terms and conditions.");
                throw new Exception("You haven't agreed to the terms and conditions.");
            }

            if(bindingResult.hasErrors()) {
                model.addAttribute("user", user);
                return "signup";
            }
    
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("deault.png");
            User result = this.userRepository.save(user);
            model.addAttribute("user", result);
            session.setAttribute("message", new Message("Successfully registered.", "alert-success"));
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went wrong. "+e.getMessage(), "alert-danger"));
            return "signup";
        }
    }
}
