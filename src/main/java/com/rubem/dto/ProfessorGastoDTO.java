package com.rubem.dto;

import java.math.BigDecimal;

public class ProfessorGastoDTO {
    private Long professorId;
    private String professorNome;
    private BigDecimal gastoRealizado;
    private BigDecimal gastoPrevisto;

    // Getters e Setters

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getProfessorNome() {
        return professorNome;
    }

    public void setProfessorNome(String professorNome) {
        this.professorNome = professorNome;
    }

    public BigDecimal getGastoRealizado() {
        return gastoRealizado;
    }

    public void setGastoRealizado(BigDecimal gastoRealizado) {
        this.gastoRealizado = gastoRealizado;
    }

    public BigDecimal getGastoPrevisto() {
        return gastoPrevisto;
    }

    public void setGastoPrevisto(BigDecimal gastoPrevisto) {
        this.gastoPrevisto = gastoPrevisto;
    }
}