package com.OnbaordWeb.Controllers;

import com.OnbaordWeb.Dtos.UserDto;
import com.OnbaordWeb.Dtos.UserRoleDto;
import com.OnbaordWeb.Models.User;
import com.OnbaordWeb.Services.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    //save user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){

        try {

            User savedUser = userServiceImpl.registerUserWithoutSpecificRole(userDto);

            if(savedUser != null) {
                return ResponseEntity.ok("Registration Successful!");
            }
            else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Error in Registration Process!");
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //find all users/////
    @GetMapping("/all")
    public ResponseEntity<?> allUsers(){
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }

    //find all users as UserDto
    @GetMapping("/allUsersDto")
    public ResponseEntity<?> allUserDtos(){
        return ResponseEntity.ok(userServiceImpl.getAllUserDtos());
    }

    //find by id
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id){

        UserDto userDto = userServiceImpl.getUserDtoByUserId(id);

        return ResponseEntity.ok(userDto);
    }

    //find by username
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username){

        int valid = userServiceImpl.checkForValidEmail(username);

        return ResponseEntity.ok(valid);
    }

    //update user
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto, @PathVariable("id") Long id) {

        userServiceImpl.updateUser(userDto, id);

        return ResponseEntity.ok("User Updated Successfully!");
    }

    //change password
    @PutMapping("/changeUserPw/{id}")
    public ResponseEntity<?> updateUserPassword(@RequestBody UserDto userDto, @PathVariable("id") Long id){

        userServiceImpl.updateUserPassword(userDto, id);

        return ResponseEntity.ok("User Updated Successfully!");
    }

    //get User and Role By user Id
    @GetMapping("/userAndRole/{id}")
    public ResponseEntity<?> getUserAndRoleById(@PathVariable("id") Long id){

        UserRoleDto userRoleDto = userServiceImpl.getUserAndRoleByUserId(id);

        return ResponseEntity.ok(userRoleDto);
    }

    //get All USERs Only
    @GetMapping("/allUsers")
    public ResponseEntity<?> getUsersOnly(){

        List<UserDto> userDtoList = userServiceImpl.allAsDtos_onlyUsers();

        return ResponseEntity.ok(userDtoList);
    }
}
