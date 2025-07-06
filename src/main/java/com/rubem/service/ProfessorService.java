package com.rubem.service;

import com.rubem.dto.ProfessorDTO;
import com.rubem.dto.ProfessorPatchDTO;
import com.rubem.model.Professor;
import com.rubem.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfessorService (ProfessorRepository repo){
        this.repo = repo;
    }

    public List<Professor> listAll() {
        return repo.findAll();
    }

    public Professor findById(Long id){
        return repo.findById(id).orElse(null);
    }


    public Professor save(Professor professor){

        String encryptedPassword = passwordEncoder.encode(professor.getSenha());

        professor.setSenha(encryptedPassword);

        return repo.save(professor);
    }

    public void validarEmailMatriculaUnico(String email, String matricula){
        if(repo.existsByEmail(email) || repo.existsByMatricula(matricula))
            throw new IllegalArgumentException("Matricula ou Email já existente");
    }

    public void delete (Long id){
        repo.deleteById(id);
    }

    public Professor update(Long id, ProfessorDTO dto) {
        Professor professor = findById(id); // já lança exceção se não encontrar

        this.validarEmailMatriculaUnico(dto.getEmail(), dto.getMatricula());

        professor.setNome(dto.getNome());
        professor.setEmail(dto.getEmail());
        professor.setEndereco(dto.getEndereco());
        professor.setMatricula(dto.getMatricula());
        professor.setTelefone(dto.getTelefone());
        professor.setSenha(passwordEncoder.encode(dto.getSenha()));

        return repo.save(professor);
    }

    public Professor partialUpdate(Long id, ProfessorPatchDTO dto) {
        Professor professor = findById(id);

        if (dto.getNome() != null) professor.setNome(dto.getNome());
        if (dto.getEndereco() != null) professor.setEndereco(dto.getEndereco());
        if (dto.getTelefone() != null) professor.setTelefone(dto.getTelefone());
        if (dto.getSenha() != null) professor.setSenha(passwordEncoder.encode(dto.getSenha()));
        if (dto.getValorHora() != null) professor.setValorHora(dto.getValorHora());
        if (dto.getLinguas() != null) professor.setLinguas(dto.getLinguas());

        return repo.save(professor);
    }
}
