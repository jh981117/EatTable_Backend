package com.lec.spring.domain.DTO;


import lombok.Data;

@Data
public class PasswordRequest {
    private String username;
    private String password;
}
