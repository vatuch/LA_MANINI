package com.lamanini.La.manini.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String delivery;
    private int total;
    private String items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone.matches("^\\+?\\d+$")) {
            this.phone = phone;
        }
    }
    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        if (delivery.equals("самовывоз") || delivery.equals("курьер")) {
            this.delivery = delivery;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(String totalString) {
        if (totalString != null && !totalString.isEmpty()) {
            String[] parts = totalString.split(" ");
            if (parts.length > 0) {
                try {
                    this.total = Integer.parseInt(parts[0]);
                } catch (NumberFormatException e) {
                    // Обработка исключения, если первая часть не является числом
                }
            }
        }
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

}