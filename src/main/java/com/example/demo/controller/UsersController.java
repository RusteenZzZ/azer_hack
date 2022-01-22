package com.example.demo.controller;

import com.example.demo.dto.Login;
import com.example.demo.dto.Logout;
import com.example.demo.dto.Register;
import com.example.demo.service.impl.UsersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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
    public Object login(@RequestBody Login login) {
        Object res = this.usersService.loginUser(login);

        return res;
    }

    @PostMapping("/register")
    public Object register(@RequestBody Register register) {

        register.setPassword(encoder().encode(register.getPassword()));

        Object res = this.usersService.registerUser(register);

        return res;
    }

    @PostMapping("/logout")
    public Object logout(@RequestBody Logout logout) {
        Object res = this.usersService.logoutUser(logout);

        return res;
    }
}
