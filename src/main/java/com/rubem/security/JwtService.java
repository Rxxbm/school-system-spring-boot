package com.rubem.security;
import com.rubem.enums.Cargo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String secret = "segredo-muito-seguro-para-jwt-12345678901234567890"; // 256 bits
    private final long expirationMillis = 1000 * 60 * 60; // 1h

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email, Cargo role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public Cargo extractRole(String token) {
        String roleName = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class); // Extrai como String

        // Converte manualmente para o enum
        try {
            return Cargo.valueOf(roleName);
        } catch (IllegalArgumentException e) {
            throw new JwtException("Cargo inválido no token: " + roleName);
        }
    }


    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Verifica se a role existe no enum
            Cargo.valueOf(claims.get("role", String.class));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}