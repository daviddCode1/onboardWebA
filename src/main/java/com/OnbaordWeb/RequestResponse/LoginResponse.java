package com.OnbaordWeb.RequestResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String role;

    public LoginResponse(Long id, String firstName, String lastName, String username, String  role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
    }
}
