package com.lamanini.La.manini.Controllers;

import com.lamanini.La.manini.models.User;
import com.lamanini.La.manini.repositories.Individual_purchaseRepository;
import com.lamanini.La.manini.repositories.PurchaseRepository;
import com.lamanini.La.manini.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private  Individual_purchaseRepository individualPurchaseRepository;

    @Autowired
    private  UserRepository userRepository;


    @GetMapping("/personal")
    public String showProfile(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user", user);
        return "personal";
    }
}