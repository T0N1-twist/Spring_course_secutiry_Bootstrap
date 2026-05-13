package com.kata.springsecurity.spring_course_springsecurity.controller;

import com.kata.springsecurity.spring_course_springsecurity.security.UserDetailsCustomClass;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String showUserPage(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        UserDetailsCustomClass details = (UserDetailsCustomClass) authentication.getPrincipal();
        model.addAttribute("user", details.getUser());
        model.addAttribute("currentUser", authentication.getName());
        model.addAttribute("currentUserRoles", authentication.getAuthorities());

        return "user";
    }
}