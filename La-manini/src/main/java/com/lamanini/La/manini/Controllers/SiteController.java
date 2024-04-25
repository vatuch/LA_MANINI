package com.lamanini.La.manini.Controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class SiteController {
    @GetMapping ("/")
    public String main(Model model) {
        model.addAttribute("title", "Главная страница");
        return "main";}

}
