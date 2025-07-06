package com.rubem.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Aluno extends Pessoa{

    @Column(unique = true)
    private String matricula;

    private String nome;
    private String endereco;
    private String telefone;


    @OneToMany(mappedBy = "aluno")
    private Set<Matricula> turmas = new HashSet<>();

    public Aluno(String matricula, String nome, String endereco, String telefone) {
        this.matricula = matricula;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Aluno(String email, String senha, String matricula, String nome, String endereco, String telefone) {
        super(email, senha);
        this.matricula = matricula;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Aluno(Long id, String email, String senha, String matricula, String nome, String endereco, String telefone) {
        super(id, email, senha);
        this.matricula = matricula;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public Aluno(){

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

    public Set<Matricula> getTurmas() {
        return turmas;
    }

    public void setTurmas(Set<Matricula> turmas) {
        this.turmas = turmas;
    }
}
