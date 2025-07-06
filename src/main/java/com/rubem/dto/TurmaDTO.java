package com.rubem.dto;

import com.rubem.enums.Lingua;
import com.rubem.enums.Nivel;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TurmaDTO {
    @NotNull
    @Future(message = "A data de início deve ser no futuro")
    private LocalDate dataInicio;

    @NotNull
    @Future(message = "A data de término deve ser no futuro")
    private LocalDate dataTermino;

    @NotNull
    private Lingua lingua;

    @NotNull
    private Nivel nivel;

    @Positive
    @NotNull
    private BigDecimal preco;

    @NotNull
    private Long professorId;

    public TurmaDTO(LocalDate dataInicio, LocalDate dataTermino, Lingua lingua, Nivel nivel, BigDecimal preco, Long professorId) {
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.lingua = lingua;
        this.nivel = nivel;
        this.preco = preco;
        this.professorId = professorId;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Lingua getLingua() {
        return lingua;
    }

    public void setLingua(Lingua lingua) {
        this.lingua = lingua;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }
}