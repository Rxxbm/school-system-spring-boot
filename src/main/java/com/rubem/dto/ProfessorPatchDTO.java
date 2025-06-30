package com.rubem.dto;

import com.rubem.enums.Lingua;
import java.math.BigDecimal;
import java.util.List;

public class ProfessorPatchDTO {

    private String nome;
    private String endereco;
    private String telefone;
    private String senha;


    private BigDecimal valorHora;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
}
