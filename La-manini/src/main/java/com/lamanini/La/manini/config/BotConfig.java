package com.lamanini.La.manini.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {

    @Value("${telegram.bot.token}")
    String botToken;

    @Value("${telegram.bot.userName}")
    String userName;
}
