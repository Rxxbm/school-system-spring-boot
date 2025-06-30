package com.rubem.dto;

import com.rubem.enums.Lingua;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public class ProfessorDTO extends PessoaDTO{
    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotBlank
    private String telefone;

    @NotBlank
    private String matricula;


    @Positive
    private BigDecimal valorHora;

    @NotEmpty
    private List<Lingua> linguas;

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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public ProfessorDTO(String nome, String endereco, String telefone, String matricula, BigDecimal valorHora, List<Lingua> linguas) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.matricula = matricula;
        this.valorHora = valorHora;
        this.linguas = linguas;
    }

    public ProfessorDTO(String email, String senha, String nome, String endereco, String telefone, String matricula, BigDecimal valorHora, List<Lingua> linguas) {
        super(email, senha);
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.matricula = matricula;
        this.valorHora = valorHora;
        this.linguas = linguas;
    }

    public ProfessorDTO(){}
}
