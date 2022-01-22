package com.example.demo.service.impl;

import com.example.demo.dto.Login;
import com.example.demo.dto.Register;
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
    public String loginUser(Login login) {

        String token = null;
        System.out.println("------------------");
        try{
            Users user = usersRepo.findByEmail(login.getEmail());
            System.out.println(user);
            if(user != null) {
                JWTToken jwtToken = new JWTToken();
                token = jwtToken.getJWTToken(login.getEmail());
                user.setToken(token);
                this.usersRepo.save(user);
            }

        } catch (Exception e){
            System.out.println(String.format("Could not find user with email %s", login.getEmail()));
        }

        return token;
    }

    @Override
    public String registerUser(Register register) {
        Users user = new Users(
                register.getName(),
                register.getEmail(),
                register.getPassword()
        );

        try{
            Users userByEmail = this.usersRepo.findByEmail(register.getEmail());
            if(userByEmail == null) {
                this.usersRepo.save(user);
            } else {
                System.out.println(String.format("The user with email %s already exists", register.getEmail()));
                return null;
            }
        }catch (Exception e) {
            System.out.println(e);
            return "Fail";
        }

        return "TEST";
    }

}
