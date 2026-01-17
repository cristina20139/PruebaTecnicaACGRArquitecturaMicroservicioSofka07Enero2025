package com.sofka.microservicios.cuentas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO que representa un reporte de un movimiento bancario.
 * Contiene información sobre la fecha, tipo, valor y saldo disponible después del movimiento.
 * 
 * 
 */

public class ReporteMovimientoDto {
    private LocalDateTime fecha;
    private String tipo;
    private BigDecimal valor;
    private BigDecimal saldoDisponible;

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }
}
