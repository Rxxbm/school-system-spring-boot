package com.rubem.repository;

import com.rubem.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);

}
