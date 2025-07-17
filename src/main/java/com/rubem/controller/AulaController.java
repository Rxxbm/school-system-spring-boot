package com.rubem.controller;

import com.rubem.dto.AulaDTO;
import com.rubem.dto.AulaResponseDTO;
import com.rubem.model.Aula;
import com.rubem.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AulaResponseDTO> criarAula(@RequestBody @Valid AulaDTO aula) {
        Aula novaAula = aulaService.criarAula(aula);
        AulaResponseDTO response = new AulaResponseDTO(novaAula);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AulaResponseDTO>> listarAulas() {
        List<AulaResponseDTO> aulas = aulaService.listarTodasAulas();
        return ResponseEntity.ok(aulas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AulaResponseDTO> buscarAulaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(aulaService.buscarAulaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aula> atualizarAula(@PathVariable Long id, @RequestBody AulaDTO aula) {
        try {
            Aula aulaAtualizada = aulaService.atualizarAula(id, aula);
            return ResponseEntity.ok(aulaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAula(@PathVariable Long id) {
        try {
            aulaService.deletarAula(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/realizada")
    public ResponseEntity<Void> marcarComoRealizada(@PathVariable Long id) {
        try {
            aulaService.marcarComoRealizada(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/postergar")
    public ResponseEntity<Aula> postergarAula(
            @PathVariable Long id,
            @RequestParam LocalDate novaData) {
        try {
            Aula aulaPostergada = aulaService.postergarAula(id, novaData);
            return ResponseEntity.ok(aulaPostergada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
