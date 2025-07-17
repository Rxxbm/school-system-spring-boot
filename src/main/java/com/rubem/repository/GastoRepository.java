package com.rubem.repository;

import com.rubem.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    @Query("SELECT g FROM Gasto g WHERE MONTH(g.data) = :mes AND YEAR(g.data) = :ano")
    List<Gasto> findByDataMonthAndDataYear(@Param("mes") int mes, @Param("ano") int ano);
}
