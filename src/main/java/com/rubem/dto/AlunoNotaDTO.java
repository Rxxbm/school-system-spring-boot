package com.rubem.dto;

import java.math.BigDecimal;

public class AlunoNotaDTO {
    private Long alunoId;
    private String alunoNome;
    private String matricula;
    private BigDecimal notaFinal;

    public AlunoNotaDTO(Long alunoId, String alunoNome, String matricula, BigDecimal notaFinal) {
        this.alunoId = alunoId;
        this.alunoNome = alunoNome;
        this.matricula = matricula;
        this.notaFinal = notaFinal;
    }

    // Getters e Setters
    public Long getAlunoId() {
        return alunoId;
    }

    public String getAlunoNome() {
        return alunoNome;
    }

    public String getMatricula() {
        return matricula;
    }

    public BigDecimal getNotaFinal() {
        return notaFinal;
    }
}