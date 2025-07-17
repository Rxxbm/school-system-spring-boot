package com.rubem.controller;

import com.rubem.dto.RelatorioFinanceiroDTO;
import com.rubem.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/mensal/{ano}")
    public ResponseEntity<List<RelatorioFinanceiroDTO>> relatorioMensal(@PathVariable int ano) {
        List<RelatorioFinanceiroDTO> relatorio = relatorioService.gerarRelatorioMensal(ano);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/anual/{ano}")
    public ResponseEntity<RelatorioFinanceiroDTO> relatorioAnual(@PathVariable int ano) {
        RelatorioFinanceiroDTO relatorio = relatorioService.gerarRelatorioAnual(ano);
        return ResponseEntity.ok(relatorio);
    }
}