package com.example.demo.controller;

import com.example.demo.dto.Login;
import com.example.demo.security.jwt.JWTToken;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping("login")
    public String login(@RequestBody Login login) {

        JWTToken jwtToken = new JWTToken();
        String token = jwtToken.getJWTToken(login.getEmail());
        return token;
//        Users user = new Users();
//        user.setUsers(username);
//        user.setToken(token);
//        return user;
    }

    @PostMapping("register")
    public String register(@RequestBody)
}
