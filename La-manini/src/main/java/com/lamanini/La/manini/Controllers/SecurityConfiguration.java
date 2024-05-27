package com.lamanini.La.manini.Controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers( "/login**", "/register", "/img/**", "/css/**", "/error**", "/order/**").permitAll();
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers("/main").authenticated();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form.loginPage("/login").permitAll();
                    form.usernameParameter("name").permitAll();
                    form.passwordParameter("password").permitAll();
                    form.loginProcessingUrl("/doLogin");
                    form.successForwardUrl("/main");
                    form.failureForwardUrl("/error");
                })
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}