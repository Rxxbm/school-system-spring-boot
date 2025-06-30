package com.rubem.repository;

import com.rubem.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);
}
