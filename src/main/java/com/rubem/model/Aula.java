package com.rubem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "aulas")
public class Aula {
    private static final BigDecimal VALOR_HORA_AULA = new BigDecimal(50);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    @FutureOrPresent
    private LocalDate data;

    private LocalTime horaInicio;
    private LocalTime horaFim;


    private boolean realizada = false;

    public Aula(){}
    public Aula(Long id, Turma turma, LocalDate data, LocalTime horaInicio, LocalTime horaFim, boolean realizada) {
        this.id = id;
        this.turma = turma;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.realizada = realizada;
    }

    @Transient
    public BigDecimal getValorHoraTotal() {
        return VALOR_HORA_AULA.add(this.getTurma().getProfessor().getValorHora());
    }

    @Transient
    public BigDecimal getCustoTotal() {
        Duration duracao = Duration.between(this.getHoraInicio(), this.getHoraFim());
        long horas = duracao.toHours();
        return this.getValorHoraTotal().multiply(new BigDecimal(horas));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }
}