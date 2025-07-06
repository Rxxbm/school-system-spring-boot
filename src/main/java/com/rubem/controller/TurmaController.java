package com.rubem.controller;

import com.rubem.dto.*;
import com.rubem.model.Professor;
import com.rubem.repository.ProfessorRepository;
import com.rubem.security.JwtService;
import com.rubem.service.TurmaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    private final TurmaService turmaService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ProfessorRepository professorRepository;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }
    @PostMapping
    public ResponseEntity<TurmaResponseDTO> createTurma(@RequestBody @Valid TurmaDTO turmaDTO) {
        TurmaResponseDTO createdTurma = turmaService.createTurma(turmaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTurma);
    }

    @GetMapping
    public ResponseEntity<List<TurmaResponseDTO>> getAllTurmas() {
        List<TurmaResponseDTO> turmas = turmaService.getAllTurmas();
        return ResponseEntity.ok(turmas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> getTurmaById(@PathVariable Long id) {
        TurmaResponseDTO turma = turmaService.getTurmaById(id);
        return ResponseEntity.ok(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaResponseDTO> updateTurma(
            @PathVariable Long id,
            @RequestBody @Valid TurmaDTO turmaDTO) {
        TurmaResponseDTO updatedTurma = turmaService.updateTurma(id, turmaDTO);
        return ResponseEntity.ok(updatedTurma);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurma(@PathVariable Long id) {
        turmaService.deleteTurma(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{turmaId}/matricular/{alunoId}")
    public ResponseEntity<String> matricularAluno(
            @PathVariable Long turmaId,
            @PathVariable Long alunoId) {
        turmaService.matricularAluno(turmaId, alunoId);
        return ResponseEntity.ok("Inserção Feita com Sucesso");
    }

    @DeleteMapping("/{turmaId}/alunos/{alunoId}")
    public ResponseEntity<Void> removerAlunoDaTurma(
            @PathVariable Long turmaId,
            @PathVariable Long alunoId) {
        turmaService.removerAlunoDaTurma(turmaId, alunoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{turmaId}/alunos")
    public ResponseEntity<List<AlunoNotaDTO>> listarAlunosDaTurma(@PathVariable Long turmaId) {
        List<AlunoNotaDTO> alunos = turmaService.listarAlunosDaTurma(turmaId);
        return ResponseEntity.ok(alunos);
    }

    @PostMapping("/atribuir-nota")
    public ResponseEntity<String> atribuirNota(
            HttpServletRequest request,
            @RequestBody @Valid MatriculaDTO dto) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token ausente ou inválido");
        }
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        Professor professor = professorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        turmaService.atribuirNotaFinal(professor.getId(), dto);
        return ResponseEntity.ok("Atribuição de nota realizada com sucesso");
    }
}