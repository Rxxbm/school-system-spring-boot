package com.rubem.repository;

import com.rubem.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    Optional<Matricula> findByAlunoIdAndTurmaId(Long alunoId, Long turmaId);
    List<Matricula> findByTurmaId(Long turmaId);
}
