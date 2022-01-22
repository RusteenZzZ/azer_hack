package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.impl.UsersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.lang.Error;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {

    private final UsersServiceImpl usersService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(){
        System.out.println("TET");
        return "HELLO";
    }

    @GetMapping("/get-user")
    public Object getUserByToken(@RequestHeader("Authorization") String token) {
        String[] strings = token.split(" ");
        if(strings.length != 2) {
            return new Error("Wrong authorization header structure!!!");
        }
        Object res = this.usersService.getUserByToken(new GetUserByToken(strings[1]));

        return res;
    }

    @PostMapping("/login")
    public Object login(@RequestBody Login login) {
        Object res = this.usersService.loginUser(login);

        return res;
    }

    @PostMapping("/register")
    public Object register(@RequestBody Register register) {

        register.setPassword(passwordEncoder.encode(register.getPassword()));

        Object res = this.usersService.registerUser(register);

        return res;
    }

    @PostMapping("/logout")
    public Object logout(@RequestBody Logout logout) {
        Object res = this.usersService.logoutUser(logout);

        return res;
    }
}
