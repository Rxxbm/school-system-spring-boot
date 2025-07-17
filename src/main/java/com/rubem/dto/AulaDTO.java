package com.rubem.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class AulaDTO {

    @NotNull(message = "O ID da turma é obrigatório")
    private Long turmaId;

    @NotNull(message = "A data da aula é obrigatória")
    @FutureOrPresent(message = "A data da aula deve ser no presente ou futuro")
    private LocalDate data;

    @NotNull(message = "A hora de início é obrigatória")
    private LocalTime horaInicio;

    @NotNull(message = "A hora de fim é obrigatória")
    private LocalTime horaFim;

    public AulaDTO() {}

    public AulaDTO(Long turmaId, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        this.turmaId = turmaId;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    // Getters e Setters

    public Long getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Long turmaId) {
        this.turmaId = turmaId;
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
}
