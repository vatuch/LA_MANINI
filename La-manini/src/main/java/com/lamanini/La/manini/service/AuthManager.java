package com.lamanini.La.manini.service;

import com.lamanini.La.manini.models.User;
import com.lamanini.La.manini.reposetories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthManager implements AuthenticationManager {

    private final UserRepository userRepository;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("custom auth pass {}", authentication.getCredentials());
        log.info("custom auth details {}", authentication.getDetails());
        log.info("custom auth login {}", authentication.getPrincipal());
        log.info("custom auth {}", authentication.isAuthenticated());
        log.info("custom auth {}", authentication.getName());

        User user = userRepository.findByEmail((String) authentication.getPrincipal());

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword(), user.getAuthorities()
        );

        return authToken;
    }
}
