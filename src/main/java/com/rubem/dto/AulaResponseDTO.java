package com.rubem.dto;

import com.rubem.model.Aula;

import java.time.LocalDate;
import java.time.LocalTime;

public class AulaResponseDTO {
    private Long id;
    private Long turmaId;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public AulaResponseDTO(Aula aula) {
        this.id = aula.getId();
        this.turmaId = aula.getTurma().getId();
        this.data = aula.getData();
        this.horaInicio = aula.getHoraInicio();
        this.horaFim = aula.getHoraFim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
