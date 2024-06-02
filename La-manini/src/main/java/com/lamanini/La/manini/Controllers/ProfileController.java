package com.lamanini.La.manini.Controllers;

import ch.qos.logback.core.model.Model;
import com.lamanini.La.manini.models.Individual_purchase;
import com.lamanini.La.manini.models.Purchase;
import com.lamanini.La.manini.models.User;
import com.lamanini.La.manini.reposetories.Individual_purchaseRepository;
import com.lamanini.La.manini.reposetories.PurchaseRepository;
import com.lamanini.La.manini.reposetories.UserRepository;
import com.lamanini.La.manini.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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


        //List<Purchase> purchases = purchaseRepository.findByUser(user);
        //List<Individual_purchase>  individualPurchases = individualPurchaseRepository.findByUser(user);

        model.addAttribute("user", user);
        //model.addAttribute("purchases", purchases);
        //model.addAttribute("individualPurchases", individualPurchases);

        return "personal";
    }
}