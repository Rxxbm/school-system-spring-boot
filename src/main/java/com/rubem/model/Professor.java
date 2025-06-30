package com.rubem.model;

import com.rubem.enums.Lingua;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Professor extends Pessoa{

    @Column(unique = true)
    private String matricula;

    private String nome;
    private String endereco;
    private String telefone;

    private BigDecimal valorHora;

    @ElementCollection
    private List<Lingua> linguas;

    @OneToMany(mappedBy = "professor")
    private Set<Aula> aulas = new HashSet<>();

    public Professor(String email, String senha, String matricula, String nome, String endereco, String telefone, BigDecimal valorHora, List<Lingua> linguas) {
        super(email, senha);
        this.matricula = matricula;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.valorHora = valorHora;
        this.linguas = linguas;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public List<Lingua> getLinguas() {
        return linguas;
    }

    public void setLinguas(List<Lingua> linguas) {
        this.linguas = linguas;
    }

    public Set<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(Set<Aula> aulas) {
        this.aulas = aulas;
    }

    public Professor(String matricula, String nome, String endereco, String telefone, BigDecimal valorHora, List<Lingua> linguas, Set<Aula> aulas) {
        this.matricula = matricula;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.valorHora = valorHora;
        this.linguas = linguas;
    }


    public Professor(){}
}