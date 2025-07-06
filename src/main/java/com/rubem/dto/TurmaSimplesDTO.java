package com.rubem.dto;

import com.rubem.enums.Lingua;
import com.rubem.enums.Nivel;
import com.rubem.model.Turma;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TurmaSimplesDTO {
    private Long id;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private Lingua lingua;
    private Nivel nivel;
    private BigDecimal preco;

    public TurmaSimplesDTO(Turma turma) {
        this.id = turma.getId();
        this.dataInicio = turma.getDataInicio();
        this.dataTermino = turma.getDataTermino();
        this.lingua = turma.getLingua();
        this.nivel = turma.getNivel();
        this.preco = turma.getPreco();
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
}