package com.lamanini.La.manini.Controllers;

import ch.qos.logback.core.model.Model;
import com.lamanini.La.manini.models.User;
import com.lamanini.La.manini.reposetories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "login";
    }


    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, ModelMap model, HttpSession session) {
        User savedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (savedUser != null) {
            session.setAttribute("loggedInUser", savedUser);
            return "redirect:/main";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}