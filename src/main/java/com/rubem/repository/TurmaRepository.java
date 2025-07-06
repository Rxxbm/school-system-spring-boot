package com.rubem.repository;

import com.rubem.dto.AlunoNotaDTO;
import com.rubem.model.Aluno;
import com.rubem.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
    boolean existsByProfessorId(Long professorId);
    @Query("SELECT NEW com.rubem.dto.AlunoNotaDTO(" +
            "a.id, a.nome, a.matricula, m.notaFinal) " +
            "FROM Matricula m " +
            "JOIN m.aluno a " +
            "WHERE m.turma.id = :turmaId")
    List<AlunoNotaDTO> findAlunosComNotasByTurmaId(@Param("turmaId") Long turmaId);
}