package com.rubem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rubem.enums.Lingua;
import com.rubem.enums.Nivel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataInicio;
    private LocalDate dataTermino;

    @Enumerated(EnumType.STRING)
    private Lingua lingua;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    @Positive
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToMany(mappedBy = "turma")
    @JsonBackReference
    private Set<Matricula> matriculas = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    private Set<Aula> aulas = new HashSet<>();

    public Turma (){}

    public Turma(Long id, LocalDate dataInicio, LocalDate dataTermino, Lingua lingua, Nivel nivel, BigDecimal preco, Professor professor, Set<Matricula> matriculas, Set<Aula> aulas) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.lingua = lingua;
        this.nivel = nivel;
        this.preco = preco;
        this.professor = professor;
        this.matriculas = matriculas;
        this.aulas = aulas;
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

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Set<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(Set<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Set<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(Set<Aula> aulas) {
        this.aulas = aulas;
    }
}
