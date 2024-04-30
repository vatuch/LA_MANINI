package com.lamanini.La.manini.Controllers;

import com.lamanini.La.manini.models.Post;
import com.lamanini.La.manini.reposetories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class SiteController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping ("/")
    public String main(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("title", "Главная страница");
        return "main";}

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

}
