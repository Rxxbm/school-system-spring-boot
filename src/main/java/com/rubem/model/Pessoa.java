package com.rubem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype") // opcional, pois DTYPE é o padrão
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String senha;

    public Pessoa() {
    }

    public Pessoa(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Pessoa(Long id, String email, String senha){
        this.id = id;
        this.senha = senha;
        this.email = email;

    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
