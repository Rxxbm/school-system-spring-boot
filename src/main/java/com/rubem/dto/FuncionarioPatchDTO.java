package com.rubem.dto;

import com.rubem.enums.Cargo;

import java.math.BigDecimal;

public class FuncionarioPatchDTO {

    private String nome;
    private String senha;
    private Cargo cargo;
    private BigDecimal salario;

    public FuncionarioPatchDTO(){}

    public FuncionarioPatchDTO(String nome, String senha, Cargo cargo, BigDecimal salario) {
        this.nome = nome;
        this.senha = senha;
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
