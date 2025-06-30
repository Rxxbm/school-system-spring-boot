package com.rubem.service;

import com.rubem.dto.AlunoDTO;
import com.rubem.dto.AlunoPatchDTO;
import com.rubem.model.Aluno;
import com.rubem.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AlunoService (AlunoRepository repo){
        this.repo = repo;
    }

    public List<Aluno> listAll() {
        return repo.findAll();
    }

    public Aluno findById(Long id){
        return repo.findById(id).orElse(null);
    }


    public Aluno save(Aluno aluno){

        String encryptedPassword = passwordEncoder.encode(aluno.getSenha());

        aluno.setSenha(encryptedPassword);

        return repo.save(aluno);
    }


    public void delete (Long id){
        repo.deleteById(id);
    }

    public void validarEmailMatriculaUnico(String email, String matricula){
        if(repo.existsByEmail(email) || repo.existsByMatricula(matricula))
            throw new IllegalArgumentException("Matricula ou Email já existente");
    }

    public Aluno update(Long id, AlunoDTO dto) {
        Aluno aluno = findById(id); // já lança exceção se não encontrar

        this.validarEmailMatriculaUnico(dto.getEmail(), dto.getMatricula());

        aluno.setNome(dto.getNome());
        aluno.setEmail(dto.getEmail());
        aluno.setEndereco(dto.getEndereco());
        aluno.setMatricula(dto.getMatricula());
        aluno.setTelefone(dto.getTelefone());
        aluno.setSenha(passwordEncoder.encode(dto.getSenha()));

        return repo.save(aluno);
    }

    public Aluno partialUpdate(Long id, AlunoPatchDTO dto) {
        Aluno aluno = findById(id);

        if (dto.getNome() != null) aluno.setNome(dto.getNome());
        if (dto.getEndereco() != null) aluno.setEndereco(dto.getEndereco());
        if (dto.getTelefone() != null) aluno.setTelefone(dto.getTelefone());
        if (dto.getSenha() != null) aluno.setSenha(passwordEncoder.encode(dto.getSenha()));

        return repo.save(aluno);
    }
}
