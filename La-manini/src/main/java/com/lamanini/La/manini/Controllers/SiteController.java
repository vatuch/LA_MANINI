package com.lamanini.La.manini.Controllers;


import com.lamanini.La.manini.models.Individual_purchase;
import com.lamanini.La.manini.models.Purchase;
import com.lamanini.La.manini.models.User;
import com.lamanini.La.manini.reposetories.Individual_purchaseRepository;
import com.lamanini.La.manini.reposetories.PurchaseRepository;
import com.lamanini.La.manini.reposetories.UserRepository;
import com.lamanini.La.manini.service.TelegramBotService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


@Controller
@RequiredArgsConstructor
public class SiteController {

    private static final Logger log = LoggerFactory.getLogger(SiteController.class);
    private final PurchaseRepository purchaseRepository;
    private final Individual_purchaseRepository individualPurchaseRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    TelegramBotService telegramBotService;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException{
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBotService);
        }
        catch (TelegramApiException e){
        }
    }

    @GetMapping("/img/{imageName}")
    public ResponseEntity<Resource> serveImage(@PathVariable String imageName) throws IOException {
        Resource resource = new ClassPathResource("static/css/img/" + imageName);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(resource.getFile().toPath())))
                .body(resource);
    }

    @PostMapping("/")
    public String postMain(Model model){
        model.addAttribute("title", "Главная страница");
        return "main";
    }

    @GetMapping ("/")
    public String main(Model model) {
        model.addAttribute("title", "Главная страница");
        return "main";
    }

    @GetMapping("/main")
    public String mainPage(Model model, HttpSession session) {
        log.info("redirect to main");
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        log.info("{}",session.getAttributeNames());
        log.info("is logged in user? {}", loggedInUser);
        if (loggedInUser != null) {
            model.addAttribute("name", loggedInUser.getName());
            return "main";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/response")
    public String responsePage() {
        return "response";
    }

    @GetMapping(value = "/css/main.css")
    @ResponseBody
    public String getMainCSS() throws IOException {
        Resource resource = new ClassPathResource("static/css/main.css");
        String css = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        return css;
    }


    @PostMapping("/purchase")
    public String createPurchase (@RequestParam("name") String name,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("delivery") String delivery,
                                  @RequestParam("total") String totalString,
                                  @RequestParam("items") String items) {
        Purchase purchase = new Purchase();
        purchase.setName(name);
        purchase.setPhone(phone);
        purchase.setDelivery(delivery);
        purchase.setTotal(totalString);
        purchase.setItems(items);
        purchaseRepository.save(purchase);
        return "responce_order";
    }

    @PostMapping("/individual_purchase")
    public String createIndividual_purchase (@RequestParam("name") String name,
                                             @RequestParam("phone") String phone,
                                             @RequestParam("wishes") String wishes){

        Individual_purchase individual_purchase = new Individual_purchase();
        individual_purchase.setName(name);
        individual_purchase.setPhone(phone);
        individual_purchase.setWishes(wishes);
        individualPurchaseRepository.save(individual_purchase);
        return "responce_order";
    }

    @PostMapping("/create-purchase")
    public String createPurchase(@ModelAttribute Purchase purchase) {
        Purchase savedPurchase = purchaseRepository.save(purchase);
        return "responce_order";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        String userPassword = user.getPassword();
        userPassword = passwordEncoder.encode(userPassword);
        user.setPassword(userPassword);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
        log.info("Get user {}", user);
        log.info("Get model {}", model);
        User savedUser = userRepository.findByEmail(user.getEmail());

        if(savedUser == null){
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }

        if(!passwordEncoder.matches(user.getPassword(), savedUser.getPassword())){
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }

        log.info("User correct");
        session.setAttribute("loggedInUser", savedUser);
        return "redirect:/main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}