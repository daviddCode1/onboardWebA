package com.OnbaordWeb.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Long userId;

    private String firstName;

    private String lastName;

    private String username;

    private String role;

    private String password;
}
