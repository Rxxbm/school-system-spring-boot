package com.rubem.service;


import com.rubem.dto.FuncionarioDTO;
import com.rubem.dto.FuncionarioPatchDTO;
import com.rubem.model.Funcionario;
import com.rubem.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public FuncionarioService (FuncionarioRepository repo) { this.repo = repo; }

    public List<Funcionario> listAll(){
        return repo.findAll();
    }

    public Funcionario findById(Long id){
        return repo.findById(id).orElse(null);
    }

    public Funcionario save(Funcionario funcionario){
        String encryptedPassword = passwordEncoder.encode(funcionario.getSenha());

        funcionario.setSenha(encryptedPassword);

        return repo.save(funcionario);
    }

    public void validarEmailUnico(String email){
        if(repo.existsByEmail(email))
            throw new IllegalArgumentException("Email já existente");
    }

    public void delete (Long id){
        repo.deleteById(id);
    }

    public Funcionario update(Long id, FuncionarioDTO dto) {
        Funcionario funcionario = this.findById(id); // já lança exceção se não encontrar

        this.validarEmailUnico(dto.getEmail());

        funcionario.setNome(dto.getNome());
        funcionario.setEmail(dto.getEmail());
        funcionario.setCargo(dto.getCargo());
        funcionario.setSalario(dto.getSalario());
        funcionario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return repo.save(funcionario);
    }

    public Funcionario partialUpdate(Long id, FuncionarioPatchDTO dto) {
        Funcionario funcionario = findById(id);

        if (dto.getNome() != null) funcionario.setNome(dto.getNome());
        if (dto.getCargo() != null) funcionario.setCargo(dto.getCargo());
        if (dto.getSalario() != null) funcionario.setSalario(dto.getSalario());
        if (dto.getSenha() != null) funcionario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return repo.save(funcionario);
    }

}
