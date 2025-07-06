package com.rubem.dto;
import com.rubem.enums.Lingua;
import com.rubem.model.Professor;

import java.math.BigDecimal;
import java.util.List;

public class ProfessorResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String endereco;
    private String telefone;
    private BigDecimal valorHora;
    private List<Lingua> linguas;
    private List<TurmaSimplesDTO> turmas;

    // Construtor que recebe a entidade Professor
    public ProfessorResponseDTO(Professor professor) {
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.email = professor.getEmail();
        this.endereco = professor.getEndereco();
        this.telefone = professor.getTelefone();
        this.valorHora = professor.getValorHora();
        this.linguas = professor.getLinguas();
        this.turmas = professor.getTurmas().stream()
                .map(TurmaSimplesDTO::new)
                .toList();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public List<TurmaSimplesDTO> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<TurmaSimplesDTO> turmas) {
        this.turmas = turmas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    // ... outros getters e setters ...
}
