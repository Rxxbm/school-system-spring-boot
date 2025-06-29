package com.rubem.dto;

public class LoginResponseDTO{
    public String token;
    public LoginResponseDTO(String token){
        this.token = "Bearer "+token;
    }
}