package com.lamanini.La.manini.service;

import com.lamanini.La.manini.models.Individual_purchase;
import com.lamanini.La.manini.models.Purchase;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBotService extends TelegramLongPollingBot {


    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.userName}")
    private String userName;


    @Override
    public String getBotUsername() {
        return "LaManini_bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendPurchaseMessage(Purchase purchase) {
        String message = "Новый заказ:\n" +
                "Имя: " + purchase.getName() + "\n" +
                "Телефон: " + purchase.getPhone() + "\n" +
                "Доставка: " + purchase.getDelivery() + "\n" +
                "Сумма: " + purchase.getTotal() + "\n" +
                "Товары: " + purchase.getItems();
        sendMessage("@vatuch", message);
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    public void sendIndividualPurchaseMessage(Individual_purchase individual_purchase) {
        String message = "Новая заявка:\n" +
                "Имя: " + individual_purchase.getName() + "\n" +
                "Телефон: " + individual_purchase.getPhone() + "\n" +
                "Пожелания: " + individual_purchase.getWishes();
        sendMessage("@vatuch", message);
    }

    private void sendMessage(String chatId, String message) {
        SendMessage request = new SendMessage(chatId, message);
        try {
            execute(request);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
