package com.OnbaordWeb.Services.Impl;

import com.OnbaordWeb.Dtos.RoleDto;
import com.OnbaordWeb.Dtos.UserDto;
import com.OnbaordWeb.Dtos.UserRoleDto;
import com.OnbaordWeb.Exceptions.ResourceNotFoundException;
import com.OnbaordWeb.Exceptions.UserAlreadyExistsException;
import com.OnbaordWeb.Exceptions.UserNotFoundException;
import com.OnbaordWeb.Models.Role;
import com.OnbaordWeb.Models.User;
import com.OnbaordWeb.Repositories.UserRepository;
import com.OnbaordWeb.Services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleServiceImpl;

    //save
    @Override
    public User saveUser(User user) {

        //check username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername() + " is already exists!");
        }

        //encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //save and return result
        return userRepository.save(user);

    }

    //save user by dto
    public User saveUserByDto(UserDto userDto, Role role) {

        //check username already exists
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException(userDto.getUsername() + " is already exists!");
        }

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setRole(role);

        //encrypt password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //save and return result
        return userRepository.save(user);

    }

    //method for add user without specific role to db
    public User registerUserWithoutSpecificRole(UserDto userDto) {
        User savedUser = null;

        //check user list from db
        List<User> users = getAllUsers();

        //check result is empty
        if (users.isEmpty()) {

            //create admin role on role db table
            Role role_Admin = roleServiceImpl.createAdminRole();

            //check role
            if (role_Admin != null) {

                return saveUserByDto(userDto, role_Admin);

            }

        } else {
            Role role_User = null;

            //save details as normal user
            //get role USER
            role_User = roleServiceImpl.getRoleByName("USER");

            //check for result
            if (role_User == null) {
                role_User = roleServiceImpl.createUserRole();
            }

            //check role
            if (role_User != null) {

                return saveUserByDto(userDto, role_User);
            }
        }

        return savedUser;
    }

    //find all
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //get all USERS(role type is user)
    public List<User> getAllUsers_onlyUsers(){
        //get user role
        Role userRole = roleServiceImpl.getRoleByName("USER");

        if(userRole != null){
            return userRepository.getUsersByRole(userRole);
        }

        return null;
    }

    //get all USERS dto(role type is user)
    public List<UserDto> allAsDtos_onlyUsers(){

        //get users
        List<User> userList_onlyUsers = getAllUsers_onlyUsers();

        //check and set into array list
        if(!userList_onlyUsers.isEmpty()){
            List<UserDto> userDtos_onlyUsers = new ArrayList<>();

            for(User user : userList_onlyUsers){
                UserDto userDto = new UserDto();

                userDto.setUserId(user.getUserId());
                userDto.setFirstName(user.getFirstName());
                userDto.setLastName(user.getLastName());
                userDto.setRole(user.getRole().getName());
                userDto.setUsername(user.getUsername());

                userDtos_onlyUsers.add(userDto);

            }
            return userDtos_onlyUsers;
        }

        return null;
    }

    //get user dtos as array
    public UserDto[] getAllUserDtos() {
        //get users list
        List<User> users = getAllUsers();

        UserDto[] userDtosArray_null = new UserDto[0];

        //check the result
        if (!users.isEmpty()) {

            //create an array of UserDto
            UserDto[] userDtosArray = new UserDto[users.size()];


            for (int i = 0; i < users.size(); i++) {

                //creating a new UserDto
                UserDto userDto = new UserDto();

                //adding values
                userDto.setUserId(users.get(i).getUserId());
                userDto.setFirstName(users.get(i).getFirstName());
                userDto.setLastName(users.get(i).getLastName());
                userDto.setUsername(users.get(i).getUsername());
                userDto.setRole(users.get(i).getRole().getName());

                //adding to array
                userDtosArray[i] = userDto;
            }
            return userDtosArray;
        }

        return userDtosArray_null;
    }

    //check for valid email
    public int checkForValidEmail(String username) {

        if (username.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
            User user = getUserByUsername(username);

            if (user != null) {
                return 0;
            } else {
                return 1;
            }
        }

        return -1;
    }

    //get user by email
    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElse(null);
    }

    //find by id
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    //update user
    @Transactional
    public void updateUser(UserDto updatedUserDto, Long id) throws ResourceNotFoundException {
        //get new Role from db
        Role newRole = roleServiceImpl.getRoleByName(updatedUserDto.getRole());

        if (newRole != null) {

            //get relevant user and map
            User user_db = findById(id).orElseThrow(() -> new UserNotFoundException("User not Found!"));

            user_db.setRole(newRole);
        } else {
            throw new ResourceNotFoundException("Role not Found!");
        }
    }

    //Method for update User Password
    @Transactional
    public void updateUserPassword(UserDto updatedUserDto, Long id) {

        //get relevant user
        User user_db = findById(id).orElseThrow(() -> new UserNotFoundException("User not Found!"));

        if (user_db != null) {
            userRepository.updateUserPasswordUserId(id, passwordEncoder.encode(updatedUserDto.getPassword()));
        }
    }

    public UserRoleDto getUserAndRoleByUserId(Long userId) {

        UserRoleDto userRoleDto = new UserRoleDto();

        //get relevant user
        User user_db = findById(userId).orElseThrow(() -> new UserNotFoundException("User not Found!"));

        UserDto userDto = new UserDto();
        RoleDto roleDto = new RoleDto();

        userDto.setUserId(userId);
        userDto.setUsername(user_db.getUsername());
        userDto.setFirstName(user_db.getFirstName());
        userDto.setLastName(user_db.getLastName());

        if (user_db.getRole() != null) {
            userDto.setRole(user_db.getRole().getName());

            roleDto.setRoleId(user_db.getRole().getRoleId());
            roleDto.setName(user_db.getRole().getName());
        }

        userRoleDto.setUserDto(userDto);
        userRoleDto.setRoleDto(roleDto);

        return userRoleDto;
    }

    //Method for get user as userDto by userId
    public UserDto getUserDtoByUserId(Long userId) {
        UserDto userDto = new UserDto();

        User user = findById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found!")
        );

        userDto.setUserId(userId);
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole().getName());

        return userDto;
    }

    //method for build userDto from user
    public UserDto buildUserDtoByUser(User user) {
        UserDto userDto = new UserDto();

        if(user != null) {

            userDto.setUserId(user.getUserId());
            userDto.setUsername(user.getUsername());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setRole(user.getRole().getName());
        }

        return userDto;
    }
}


