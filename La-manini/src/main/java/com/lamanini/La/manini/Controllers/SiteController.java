package com.lamanini.La.manini.Controllers;


import com.lamanini.La.manini.models.Purchase;
import com.lamanini.La.manini.reposetories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


@ComponentScan()
@Controller

public class SiteController {

    @GetMapping("/img/{imageName}")
    public ResponseEntity<Resource> serveImage(@PathVariable String imageName) throws IOException {
        Resource resource = new ClassPathResource("static/css/img/" + imageName);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(resource.getFile().toPath())))
                .body(resource);
    }

    @GetMapping ("/")
    public String main(Model model) {
        model.addAttribute("title", "Главная страница");
        return "main";}

    @GetMapping("/main")
    public String mainPage() {
        return "main";
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


    @Autowired
    private PurchaseRepository purchaseRepository;

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


}
