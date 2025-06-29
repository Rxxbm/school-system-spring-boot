package com.rubem.model;

import com.rubem.enums.Lingua;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String matricula;

    private String nome;
    private String endereco;
    private String telefone;

    private BigDecimal valorHora;

    @ElementCollection
    private List<Lingua> linguas;

    @OneToOne
    @JoinColumn(name = "pessoa_id", nullable = false, unique = true)
    private Pessoa pessoa;

    @OneToMany(mappedBy = "professor")
    private Set<Aula> aulas = new HashSet<>();


    // Getters e Setters
}