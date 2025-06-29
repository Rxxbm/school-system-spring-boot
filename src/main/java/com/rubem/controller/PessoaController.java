package com.rubem.controller;

import com.rubem.model.Pessoa;
import com.rubem.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    private final PessoaService service;

    public PessoaController(PessoaService Pessoa) {
        this.service = Pessoa;
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> getAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable Long id) {
        Pessoa Pessoa = service.findById(id);
        return ResponseEntity.ok(Pessoa);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}