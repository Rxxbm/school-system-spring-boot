package com.rubem.controller;


import com.rubem.dto.MeResponseDTO;
import com.rubem.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
public class UserController {

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public MeResponseDTO getUserInfo(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token ausente ou inválido");
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);
        String role = jwtService.extractRole(token);

        return new MeResponseDTO(email, role);
    }
}