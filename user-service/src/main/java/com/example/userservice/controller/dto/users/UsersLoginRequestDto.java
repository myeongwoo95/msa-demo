package com.example.userservice.controller.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersLoginRequestDto {
    private String email;
    private String password;
}
