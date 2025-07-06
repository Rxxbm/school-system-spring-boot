package com.rubem.dto;

import com.rubem.enums.Cargo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class FuncionarioDTO extends PessoaDTO{
    @NotBlank
    private String nome;

    @NotNull
    private Cargo cargo;

    @Positive
    @NotNull
    private BigDecimal salario;

    public FuncionarioDTO(){}

    public FuncionarioDTO(String nome, Cargo cargo, BigDecimal salario) {
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    public FuncionarioDTO(String email, String senha, String nome, Cargo cargo, BigDecimal salario) {
        super(email, senha);
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
