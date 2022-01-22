package com.example.demo.controller;

import com.example.demo.dto.Login;
import com.example.demo.dto.Register;
import com.example.demo.entity.Users;
import com.example.demo.security.jwt.JWTToken;
import com.example.demo.service.impl.UsersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UsersController {

    private final UsersServiceImpl usersService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/")
    public String home(){
        System.out.println("TET");
        return "HELLO";
    }

    @PostMapping("/login")
    public String login(@RequestBody Login login) {
        String res = this.usersService.loginUser(login);

        return res;
    }

    @PostMapping("/register")
    public String register(@RequestBody Register register) {

        register.setPassword(encoder().encode(register.getPassword()));

        String res = this.usersService.registerUser(register);

        return res;
    }
}
