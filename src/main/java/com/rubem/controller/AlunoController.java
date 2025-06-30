package com.rubem.controller;

import com.rubem.dto.AlunoPatchDTO;
import com.rubem.dto.LoginResponseDTO;
import com.rubem.dto.AlunoDTO;
import com.rubem.dto.PessoaDTO;
import com.rubem.enums.Cargo;
import com.rubem.model.Aluno;
import com.rubem.repository.AlunoRepository;
import com.rubem.security.JwtService;
import com.rubem.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    private final AlunoService service;

    public AlunoController(AlunoService aluno) {
        this.service = aluno;
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> getAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getById(@PathVariable Long id) {
        Aluno aluno = service.findById(id);
        return ResponseEntity.ok(aluno);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid PessoaDTO dto) {
        Aluno aluno = alunoRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getSenha(), aluno.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        Cargo role = Cargo.ALUNO;

        return ResponseEntity.ok(new LoginResponseDTO(jwtService.generateToken(aluno.getEmail(), role)));
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Aluno> register(@RequestBody @Valid AlunoDTO dto){
        service.validarEmailMatriculaUnico(dto.getEmail(), dto.getMatricula());
        Aluno newAluno = new Aluno(dto.getEmail(), dto.getSenha(), dto.getMatricula(), dto.getNome(), dto.getEndereco(), dto.getTelefone());
        return ResponseEntity.status(201).body(service.save(newAluno));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Long id, @RequestBody @Valid AlunoDTO dto) {
        Aluno alunoAtualizado = service.update(id, dto);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Aluno> patch(@PathVariable Long id, @RequestBody AlunoPatchDTO dto) {
        Aluno alunoAtualizado = service.partialUpdate(id, dto);
        return ResponseEntity.ok(alunoAtualizado);
    }


}