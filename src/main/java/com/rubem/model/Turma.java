package com.rubem.model;

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

    @ManyToMany
    @JoinTable(name = "turma_aluno",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id"))
    private Set<Aluno> alunos = new HashSet<>();

    @OneToMany(mappedBy = "turma")
    private Set<Aula> aulas = new HashSet<>();
}
