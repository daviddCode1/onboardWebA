package com.OnbaordWeb.Controllers;


import com.OnbaordWeb.Dtos.UserDto;
import com.OnbaordWeb.RequestResponse.LoginRequest;
import com.OnbaordWeb.Services.Impl.AuthServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    //authenticate user to log
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){

        UserDto userDto = authServiceImpl.authenticateUser(loginRequest);

        if(userDto != null) {
            return ResponseEntity.ok(userDto);
        }else {

            return ResponseEntity.notFound().build();

        }

    }
}