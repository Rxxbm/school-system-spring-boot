package com.rubem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class MatriculaDTO {
    @NotNull
    private Long aluno;

    @NotNull
    private Long turma;

    @NotNull
    @Positive
    private BigDecimal notaFinal;

    public MatriculaDTO(Long aluno, Long turma, BigDecimal notaFinal) {
        this.aluno = aluno;
        this.turma = turma;
        this.notaFinal = notaFinal;
    }

    public Long getAluno() {
        return aluno;
    }

    public void setAluno(Long aluno) {
        this.aluno = aluno;
    }

    public Long getTurma() {
        return turma;
    }

    public void setTurma(Long turma) {
        this.turma = turma;
    }

    public BigDecimal getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(BigDecimal notaFinal) {
        this.notaFinal = notaFinal;
    }
}
