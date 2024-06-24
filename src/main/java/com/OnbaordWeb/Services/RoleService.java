package com.OnbaordWeb.Services;

import com.OnbaordWeb.Models.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {

    Role saveRole(Role role);

    List<Role> getAllRoles();

    Role getRoleByName(String name);

    Optional<Role> findById(Long id);

}