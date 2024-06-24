package com.OnbaordWeb.Services;

import com.OnbaordWeb.Models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUserByUsername(String username);

    Optional<User> findById(Long id);

}