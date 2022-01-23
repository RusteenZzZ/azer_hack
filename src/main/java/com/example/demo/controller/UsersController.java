package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.impl.UsersServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.Error;

@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "https://azercell-2022.web.app")
@CrossOrigin()
public class UsersController {

    private final UsersServiceImpl usersService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(){
        System.out.println("TET");
        return "HELLO";
    }

    @GetMapping("/get-user")
    public Object getUserByToken(
            @RequestHeader("Authorization") String token
    ) {
        String[] strings = token.split(" ");
        if(strings.length != 2) {
            return new ErrorMessage("Wrong authorization header structure!!!");
        }
        Object res = this.usersService.getUserByToken(new GetUserByToken(strings[1]));

        return res;
    }

    @PostMapping("/login")
    public Object login(
            @RequestBody Login login,
            HttpServletResponse response
    ) {
        Object res = this.usersService.loginUser(login);

        response.setHeader("Access-Control-Allow-Origin:", "*");
        return res;
    }

    @PostMapping("/register")
    public Object register(
            @RequestBody Register register
    ) {
        register.setPassword(passwordEncoder.encode(register.getPassword()));

        Object res = this.usersService.registerUser(register);

        return res;
    }

    @PostMapping("/logout")
    public Object logout(
            @RequestBody Logout logout
    ) {
        Object res = this.usersService.logoutUser(logout);

        return res;
    }
}
