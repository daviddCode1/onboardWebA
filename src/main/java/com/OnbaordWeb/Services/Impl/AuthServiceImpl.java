package com.OnbaordWeb.Services.Impl;



import com.OnbaordWeb.Dtos.UserDto;
import com.OnbaordWeb.Models.User;
import com.OnbaordWeb.RequestResponse.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final UserServiceImpl userServiceImpl;


    //method for authenticate user by loginRequest
    public UserDto authenticateUser(LoginRequest loginRequest){

        //check username for blank
        if(!loginRequest.getUsername().isBlank()){
            //get user from db
            User user = userServiceImpl.getUserByUsername(loginRequest.getUsername());

            //check result
            if(user != null){
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                //check password match with loginRequest
                if(encoder.matches(loginRequest.getPassword(), user.getPassword())){
                   return userServiceImpl.buildUserDtoByUser(user);
                }
            }
        }

        return null;
    }
}
