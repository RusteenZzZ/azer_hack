package com.example.demo.service;

import com.example.demo.dto.GetUserByToken;
import com.example.demo.dto.Login;
import com.example.demo.dto.Logout;
import com.example.demo.dto.Register;

public interface UsersService {

    public Object getUserByToken(GetUserByToken getUserByToken);

    public Object loginUser(Login login);

    public Object registerUser(Register register);

    public Object logoutUser(Logout logout);

}
