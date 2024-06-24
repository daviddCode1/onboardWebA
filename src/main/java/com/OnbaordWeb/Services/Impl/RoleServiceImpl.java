package com.OnbaordWeb.Services.Impl;


import com.OnbaordWeb.Dtos.RoleDto;
import com.OnbaordWeb.Exceptions.ResourceAlreadyExistsException;
import com.OnbaordWeb.Exceptions.ResourceNotFoundException;
import com.OnbaordWeb.Models.Role;
import com.OnbaordWeb.Repositories.RoleRepository;
import com.OnbaordWeb.Services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    //save
    @Override
    public Role saveRole(Role role) {
        //check username already exists
        if(roleRepository.existsByName(role.getName())){
            throw new ResourceAlreadyExistsException(role.getName() + " is already exists!");
        }

        //convert to uppercase
        role.setName(role.getName().toUpperCase());

        //save and return result
        return roleRepository.save(role);
    }

    //method for create a role for ADMIN
    public Role createAdminRole(){
        //create a new role called admin
        Role roleAdmin = new Role();

        //assign values
        roleAdmin.setName("ADMIN");

        //save
        return saveRole(roleAdmin);
    }

    //method for create a role for USER
    public Role createUserRole(){
        //create a new role called User
        Role roleUser = new Role();

        //assign values
        roleUser.setName("USER");

        //save
        return saveRole(roleUser);
    }

    //findAll
    @Override
    public List<Role> getAllRoles() {

        return roleRepository.findAll();
    }

    //get all role dtos
    public List<RoleDto> getAllRoleDtos(){

        List<RoleDto> roleDtos = new ArrayList<>();
        List<Role> roles = getAllRoles();

        if(roles.size() > 0){

            for(Role role : roles){
                RoleDto roleDto = new RoleDto();

                roleDto.setRoleId(role.getRoleId());
                roleDto.setName(role.getName());

                roleDtos.add(roleDto);
            }
        }
        return roleDtos;
    }

    //get role by name
    @Override
    public Role getRoleByName(String roleName) {

        return roleRepository.getRoleByName(roleName);
    }

    //get role dto by name
    public RoleDto getRoleDtoByName(String roleName){

        RoleDto roleDto = new RoleDto();

        Role roleFromDB = getRoleByName(roleName);

        if(roleFromDB != null){
            roleDto.setRoleId(roleFromDB.getRoleId());
            roleDto.setName(roleFromDB.getName());
        }

        return roleDto;
    }

    //find by id
    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }


    //Method for update Role
    public Role updateRole(Role updatedRole, Long id){

        Role roleToUpdate = null;


        Optional<Role> optionalRole = findById(id);

        if(optionalRole.isPresent()){
            Role role = optionalRole.get();

            role.setName(updatedRole.getName());

            roleToUpdate = saveRole(role);
        }
        else{
            throw new ResourceNotFoundException("Role not Found!");
        }

        return roleToUpdate;
    }

    //method for check for role by name if it is exists
    public boolean isRoleExists(String roleName){

        return roleRepository.existsByName(roleName);
    }


}