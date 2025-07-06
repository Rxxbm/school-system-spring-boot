package com.rubem.controller;

import com.rubem.dto.*;
import com.rubem.enums.Cargo;
import com.rubem.model.Aluno;
import com.rubem.model.Professor;
import com.rubem.repository.ProfessorRepository;
import com.rubem.security.JwtService;
import com.rubem.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final ProfessorService service;

    public ProfessorController(ProfessorService professor) {
        this.service = professor;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponseDTO>> getAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> getById(@PathVariable Long id) {
        ProfessorResponseDTO professor = service.findById(id);
        return ResponseEntity.ok(professor);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid PessoaDTO dto) {
        Professor professor = professorRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getSenha(), professor.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        Cargo role = Cargo.PROFESSOR;

        return ResponseEntity.ok(new LoginResponseDTO(jwtService.generateToken(professor.getEmail(), role)));
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Professor> register(@RequestBody @Valid ProfessorDTO dto){
        service.validarEmailMatriculaUnico(dto.getEmail(), dto.getMatricula());
        Professor newProfessor = new Professor(dto.getEmail(), dto.getSenha(), dto.getMatricula(), dto.getNome(), dto.getEndereco(), dto.getTelefone(), dto.getValorHora(), dto.getLinguas());
        return ResponseEntity.status(201).body(service.save(newProfessor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ProfessorDTO dto) {
        ProfessorResponseDTO professorAtualizado = service.update(id, dto);
        return ResponseEntity.ok(professorAtualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> patch(@PathVariable Long id, @RequestBody ProfessorPatchDTO dto) {
        ProfessorResponseDTO professorAtualizado = service.partialUpdate(id, dto);
        return ResponseEntity.ok(professorAtualizado);
    }
}