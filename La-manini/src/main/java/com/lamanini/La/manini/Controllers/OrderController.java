package com.lamanini.La.manini.Controllers;

import com.lamanini.La.manini.models.Purchase;

import com.lamanini.La.manini.models.Purchase;
import com.lamanini.La.manini.reposetories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
    @Autowired
    private PurchaseRepository purchaseRepository;

    @PostMapping("/order")
    public String createOrder(@RequestParam("name") String name,
                              @RequestParam("phone") String phone,
                              @RequestParam("delivery") String delivery,
                              @RequestParam("total") int total,
                              @RequestParam("items") String items) {
        Purchase purchase = new Purchase();
        purchase.setName(name);
        purchase.setPhone(phone);
        purchase.setDelivery(delivery);
        purchase.setTotal(total);
        purchase.setItems(items);
        purchaseRepository.save(purchase);

        return "redirect:/success";
    }
}

