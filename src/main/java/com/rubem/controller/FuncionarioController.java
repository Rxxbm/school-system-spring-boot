package com.rubem.controller;

import com.rubem.dto.*;
import com.rubem.enums.Cargo;
import com.rubem.model.Aluno;
import com.rubem.model.Funcionario;
import com.rubem.repository.AlunoRepository;
import com.rubem.repository.FuncionarioRepository;
import com.rubem.security.JwtService;
import com.rubem.service.AlunoService;
import com.rubem.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService funcionario) {
        this.service = funcionario;
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> getAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getById(@PathVariable Long id) {
        Funcionario funcionario = service.findById(id);
        return ResponseEntity.ok(funcionario);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid PessoaDTO dto) {
        Funcionario funcionario = funcionarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getSenha(), funcionario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        Cargo role = funcionario.getCargo();

        return ResponseEntity.ok(new LoginResponseDTO(jwtService.generateToken(funcionario.getEmail(), role)));
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Funcionario> register(@RequestBody @Valid FuncionarioDTO dto){
        service.validarEmailUnico(dto.getEmail());
        Funcionario newFuncionario = new Funcionario(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getCargo(), dto.getSalario());
        return ResponseEntity.status(201).body(service.save(newFuncionario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> update(@PathVariable Long id, @RequestBody @Valid FuncionarioDTO dto) {
        Funcionario funcionarioAtualizado = service.update(id, dto);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Funcionario> patch(@PathVariable Long id, @RequestBody FuncionarioPatchDTO dto) {
        Funcionario funcionarioAtualizado = service.partialUpdate(id, dto);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

}
