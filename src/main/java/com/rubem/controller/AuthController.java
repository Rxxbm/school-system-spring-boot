package com.rubem.controller;

import com.rubem.dto.LoginResponseDTO;
import com.rubem.dto.PessoaDTO;
import com.rubem.model.Pessoa;
import com.rubem.repository.PessoaRepository;
import com.rubem.security.JwtService;
import com.rubem.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final PessoaService pessoaService;

    public AuthController(PessoaService pessoa){
        this.pessoaService = pessoa;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid PessoaDTO dto) {
        Pessoa pessoa = pessoaRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getSenha(), pessoa.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String role = "USER";

        return ResponseEntity.ok(new LoginResponseDTO(jwtService.generateToken(pessoa.getEmail(), role)));
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Pessoa> register(@RequestBody @Valid PessoaDTO dto){
        Pessoa newPessoa = new Pessoa(dto.getEmail(), dto.getSenha());
        return ResponseEntity.status(201).body(pessoaService.save(newPessoa));
    }
}

