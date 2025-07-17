package com.rubem.repository;

import com.rubem.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Long> {
    @Query("SELECT a FROM Aula a WHERE a.realizada = true AND MONTH(a.data) = :mes AND YEAR(a.data) = :ano")
    List<Aula> findByRealizadaTrueAndDataMonthAndDataYear(@Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT a FROM Aula a WHERE a.realizada = false AND MONTH(a.data) = :mes AND YEAR(a.data) = :ano")
    List<Aula> findByRealizadaFalseAndDataMonthAndDataYear(@Param("mes") int mes, @Param("ano") int ano);

    @Query("SELECT a FROM Aula a WHERE a.turma.professor.id = :professorId")
    List<Aula> findByTurmaProfessorId(@Param("professorId") Long professorId);

    @Query("SELECT a FROM Aula a WHERE a.turma.professor.id = :professorId AND a.realizada = true AND MONTH(a.data) = :mes AND YEAR(a.data) = :ano")
    List<Aula> findByTurmaProfessorIdAndRealizadaTrueAndDataMonthAndDataYear(
            @Param("professorId") Long professorId,
            @Param("mes") int mes,
            @Param("ano") int ano);

    @Query("SELECT a FROM Aula a WHERE a.turma.professor.id = :professorId AND a.realizada = false AND MONTH(a.data) = :mes AND YEAR(a.data) = :ano")
    List<Aula> findByTurmaProfessorIdAndRealizadaFalseAndDataMonthAndDataYear(
            @Param("professorId") Long professorId,
            @Param("mes") int mes,
            @Param("ano") int ano);
}
