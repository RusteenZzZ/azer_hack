package com.example.demo.service.impl;

import com.example.demo.dto.*;
import com.example.demo.dto.Error;
import com.example.demo.entity.Users;
import com.example.demo.repository.UsersRepo;
import com.example.demo.security.jwt.JWTToken;
import com.example.demo.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepo usersRepo;

    @Override
    public Object getUserByToken(GetUserByToken getUserByToken) {
        try {
            Users user = this.usersRepo.findByToken(getUserByToken.getToken());
            if(user == null) {
                System.out.println(
                        String.format("Could not find user with such %s token", getUserByToken.getToken())
                );
                return new Error(
                        String.format("Could not find user with such %s token", getUserByToken.getToken())
                );
            } else {
                return new User(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                );
            }
        } catch (Exception e) {
            System.out.println(e);
            return new Error(e.toString());
        }
    }

    @Override
    public Object loginUser(Login login) {

        String token = null;
        try{
            Users user = usersRepo.findByEmail(login.getEmail());
            System.out.println(user);
            if(user != null) {
                JWTToken jwtToken = new JWTToken();
                token = jwtToken.getJWTToken(login.getEmail());
                user.setToken(token);
                this.usersRepo.save(user);
                return new LoginSuccess(
                        token,
                        new User(
                                user.getId(),
                                user.getName(),
                                user.getEmail()
                        )
                );
            }
            System.out.println(
                    String.format("Could not find user with email %s", login.getEmail())
            );
            return new Error(
                    String.format("Could not find user with email %s", login.getEmail())
            );

        } catch (Exception e){
            System.out.println(e);
            return new Error(e.toString());
        }
    }

    @Override
    public Object registerUser(Register register) {
        Users user = new Users(
                register.getName(),
                register.getEmail(),
                register.getPassword()
        );

        try{
            Users userByEmail = this.usersRepo.findByEmail(register.getEmail());
            if(userByEmail == null) {
                this.usersRepo.save(user);
                return new RegisterSuccess(true);
            } else {
                System.out.println(String.format("The user with email %s already exists", register.getEmail()));
                return new Error(String.format("The user with email %s already exists", register.getEmail()));
            }
        }catch (Exception e) {
            System.out.println(e);
            return new Error(e.toString());
        }
    }

    @Override
    public Object logoutUser(Logout logout) {
        try {
            Users userByToken = this.usersRepo.findByToken(logout.getToken());

            if(userByToken == null) {
                return new Error(String.format("There is no a user with such %s token", logout.getToken()));
            } else {
                userByToken.setToken(null);
                return new LogoutSuccess(true);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new Error(e.toString());
        }
    }
}
