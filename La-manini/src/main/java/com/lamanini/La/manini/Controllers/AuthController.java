package com.lamanini.La.manini.Controllers;

import ch.qos.logback.core.model.Model;
import com.lamanini.La.manini.models.User;
import com.lamanini.La.manini.reposetories.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
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
        log.info("auth get user {}", user);
        User savedUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (savedUser != null) {
            SecurityContext context = SecurityContextHolder.createEmptyContext(); // Контейнер для сессии

             UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword(), user.getAuthorities()
            ); // Токен сессии
            context.setAuthentication(token); // Кладем токен в контейнер
            SecurityContextHolder.setContext(context);
            log.info("c {}",token.getCredentials());
            log.info("p {}",token.getPrincipal());
            log.info("as {}",token.getAuthorities());
            log.info("A {}",context.getAuthentication());
            session.setAttribute("loggedInUser", savedUser);
            log.info("s {}",session.getAttributeNames());
            return "redirect:/main";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}