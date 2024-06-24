package com.OnbaordWeb.Repositories;

import com.OnbaordWeb.Models.Role;
import com.OnbaordWeb.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //get user by username
    Optional<User> findByUsername(String username);

    //check user exists by username
    boolean existsByUsername(String username);

    //update password by userId
    @Modifying
    @Query("UPDATE User as user set user.password = ?2 where user.userId = ?1")
    void updateUserPasswordUserId(Long userId, String password);

    //guery for get Users by role
    @Query("SELECT user FROM User as user WHERE user.role = :role")
    List<User> getUsersByRole(Role role);
}
///