package com.rubem.controller;

import com.rubem.model.Gasto;
import com.rubem.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @GetMapping
    public List<Gasto> listar() {
        return gastoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gasto> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(gastoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Gasto> criar(@RequestBody Gasto gasto) {
        Gasto criado = gastoService.salvar(gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gasto> atualizar(@PathVariable Long id, @RequestBody Gasto gasto) {
        return ResponseEntity.ok(gastoService.atualizar(id, gasto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        gastoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
