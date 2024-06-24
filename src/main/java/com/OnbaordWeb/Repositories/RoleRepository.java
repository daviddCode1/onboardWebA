package com.OnbaordWeb.Repositories;

import com.OnbaordWeb.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    //check role exists by name
    boolean existsByName(String name);

    //get role by name
    @Query("SELECT DISTINCT role FROM Role as role WHERE name = ?1")
    Role getRoleByName(String roleName);


}