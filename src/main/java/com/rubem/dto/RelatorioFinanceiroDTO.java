package com.rubem.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public class RelatorioFinanceiroDTO {
    private YearMonth periodo;
    private BigDecimal valorArrecadado;
    private BigDecimal gastoRealizado;
    private BigDecimal gastoPrevisto;
    private BigDecimal saldo;

    // Novo campo para detalhamento por professor
    private List<ProfessorGastoDTO> gastosPorProfessor;

    // Getters e Setters
    public YearMonth getPeriodo() {
        return periodo;
    }

    public void setPeriodo(YearMonth periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getValorArrecadado() {
        return valorArrecadado;
    }

    public void setValorArrecadado(BigDecimal valorArrecadado) {
        this.valorArrecadado = valorArrecadado;
    }

    public BigDecimal getGastoRealizado() {
        return gastoRealizado;
    }

    public void setGastoRealizado(BigDecimal gastoRealizado) {
        this.gastoRealizado = gastoRealizado;
    }

    public BigDecimal getGastoPrevisto() {
        return gastoPrevisto;
    }

    public void setGastoPrevisto(BigDecimal gastoPrevisto) {
        this.gastoPrevisto = gastoPrevisto;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<ProfessorGastoDTO> getGastosPorProfessor() {
        return gastosPorProfessor;
    }

    public void setGastosPorProfessor(List<ProfessorGastoDTO> gastosPorProfessor) {
        this.gastosPorProfessor = gastosPorProfessor;
    }
}