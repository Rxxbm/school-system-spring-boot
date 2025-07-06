package com.rubem.dto;

import com.rubem.enums.Lingua;
import com.rubem.enums.Nivel;
import com.rubem.model.Turma;

import java.math.BigDecimal;
import java.time.LocalDate;


public class TurmaResponseDTO {
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private Lingua lingua;
    private Nivel nivel;
    private BigDecimal preco;
    private Long professorId;
    private String professorNome;

    // Getters e Setters

    public TurmaResponseDTO(){}

    public TurmaResponseDTO(Long id, LocalDate dataInicio, LocalDate dataTermino, Lingua lingua, Nivel nivel, BigDecimal preco, Long professorId, String professorNome) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.lingua = lingua;
        this.nivel = nivel;
        this.preco = preco;
        this.professorId = professorId;
        this.professorNome = professorNome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getProfessorNome() {
        return professorNome;
    }

    public void setProfessorNome(String professorNome) {
        this.professorNome = professorNome;
    }
}
