package com.rubem.model;

import com.rubem.enums.Cargo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

@Entity
public class Funcionario extends Pessoa{
    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    private BigDecimal salario;

    private String nome;

    public Funcionario(){}

    public Funcionario(String nome, Cargo cargo, BigDecimal salario) {
        this.cargo = cargo;
        this.salario = salario;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Funcionario(String nome, String email, String senha, Cargo cargo, BigDecimal salario) {
        super(email, senha);
        this.cargo = cargo;
        this.nome = nome;
        this.salario = salario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}
