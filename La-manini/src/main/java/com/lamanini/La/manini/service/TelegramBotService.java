package com.lamanini.La.manini.service;

import com.lamanini.La.manini.config.BotConfig;
import com.lamanini.La.manini.models.Individual_purchase;
import com.lamanini.La.manini.models.Purchase;
import com.lamanini.La.manini.reposetories.Individual_purchaseRepository;
import com.lamanini.La.manini.reposetories.PurchaseRepository;
import lombok.Data;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.session.SessionIdChangedEvent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.util.List;

@Component
public class TelegramBotService extends TelegramLongPollingBot {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private Individual_purchaseRepository individualPurchaseRepository;

    final BotConfig config;

    public TelegramBotService(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getUserName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                case "/purchase":
                    sendLastPurchaseMessage(chatId);
                    break;
                case "/order":
                    sendLastIndividualPurchaseMessage(chatId);
                    break;
                default:
                    sendMessage(chatId, "Извините, не могу понять, что вы от меня хотите");
            }
        }


    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет, " + name + ", могу рассказать информацию о твоем заказе для этого выбери функцию /purchase или /order";
        sendMessage(chatId, answer);
    }

    private void sendLastPurchaseMessage(long chatId) {
        Purchase latestPurchase = purchaseRepository.findLatestPurchase();
        if (latestPurchase != null) {
            String message = "Последний заказ:\n" +
                    "Имя: " + latestPurchase.getName() + "\n" +
                    "Телефон: " + latestPurchase.getPhone() + "\n" +
                    "Доставка: " + latestPurchase.getDelivery() + "\n" +
                    "Сумма: " + latestPurchase.getTotal() + "\n" +
                    "Товары: " + latestPurchase.getItems();
            sendMessage(chatId, message);
        }
        else {
            sendMessage(chatId, "Извините, нет информации о заказе");
        }
    }

    private void sendLastIndividualPurchaseMessage(long chatId) {
        Individual_purchase latestIndividualPurchase = individualPurchaseRepository.findLatestIndividualPurchase();
        if (latestIndividualPurchase != null) {
            String message = "Последний заказ:\n" +
                    "Имя: " + latestIndividualPurchase.getName() + "\n" +
                    "Телефон: " + latestIndividualPurchase.getPhone() + "\n" +
                    "Пожелания заказчика: " + latestIndividualPurchase.getWishes();
            sendMessage(chatId, message);
        }
        else {
            sendMessage(chatId, "Извините, нет информации о заказе");
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }

}


