package com.example.demo.service;

import com.example.demo.dto.Login;
import com.example.demo.dto.Register;

public interface UsersService {

    public String loginUser(Login login);

    public String registerUser(Register register);

}
