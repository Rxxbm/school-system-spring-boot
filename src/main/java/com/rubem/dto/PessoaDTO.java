package com.rubem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PessoaDTO {
    private Long id;

    @Email
    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    // Construtor vazio (necessário para deserialização)
    public PessoaDTO() {
    }

    // Construtor com parâmetros
    public PessoaDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public PessoaDTO(Long id, String email, String senha) {
        this.email = email;
        this.senha = senha;
        this.id = id;
    }

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
