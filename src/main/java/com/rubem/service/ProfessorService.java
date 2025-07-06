package com.rubem.service;

import com.rubem.dto.ProfessorDTO;
import com.rubem.dto.ProfessorPatchDTO;
import com.rubem.dto.ProfessorResponseDTO;
import com.rubem.model.Professor;
import com.rubem.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfessorService (ProfessorRepository repo){
        this.repo = repo;
    }

    public List<ProfessorResponseDTO> listAll() {
        return repo.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ProfessorResponseDTO findById(Long id){
        Professor prof = repo.findById(id).orElse(null);
        return this.convertToResponseDTO(prof);
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

    public ProfessorResponseDTO update(Long id, ProfessorDTO dto) {
        Professor professor = repo.findById(id).orElse(null); // já lança exceção se não encontrar

        this.validarEmailMatriculaUnico(dto.getEmail(), dto.getMatricula());

        professor.setNome(dto.getNome());
        professor.setEmail(dto.getEmail());
        professor.setEndereco(dto.getEndereco());
        professor.setMatricula(dto.getMatricula());
        professor.setTelefone(dto.getTelefone());
        professor.setSenha(passwordEncoder.encode(dto.getSenha()));

        return this.convertToResponseDTO(repo.save(professor));
    }

    public ProfessorResponseDTO partialUpdate(Long id, ProfessorPatchDTO dto) {
        Professor professor = repo.findById(id).orElse(null); // já lança exceção se não encontrar

        if (dto.getNome() != null) professor.setNome(dto.getNome());
        if (dto.getEndereco() != null) professor.setEndereco(dto.getEndereco());
        if (dto.getTelefone() != null) professor.setTelefone(dto.getTelefone());
        if (dto.getSenha() != null) professor.setSenha(passwordEncoder.encode(dto.getSenha()));
        if (dto.getValorHora() != null) professor.setValorHora(dto.getValorHora());
        if (dto.getLinguas() != null) professor.setLinguas(dto.getLinguas());

        return this.convertToResponseDTO(repo.save(professor));
    }

    ProfessorResponseDTO convertToResponseDTO(Professor prof){
        ProfessorResponseDTO dto = new ProfessorResponseDTO(prof);
        return dto;
    }
}
