package com.rubem.service;

import com.rubem.model.Pessoa;
import com.rubem.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final PessoaRepository repo;

    public PessoaService (PessoaRepository repo){
        this.repo = repo;
    }

    public List<Pessoa> listAll() {
        return repo.findAll();
    }

    public Pessoa findById(Long id){
        return repo.findById(id).orElse(null);
    }


    public Pessoa save(Pessoa pessoa){

        String encryptedPassword = passwordEncoder.encode(pessoa.getSenha());

        pessoa.setSenha(encryptedPassword);

        return repo.save(pessoa);
    }


    public void delete (Long id){
        repo.deleteById(id);
    }
}
