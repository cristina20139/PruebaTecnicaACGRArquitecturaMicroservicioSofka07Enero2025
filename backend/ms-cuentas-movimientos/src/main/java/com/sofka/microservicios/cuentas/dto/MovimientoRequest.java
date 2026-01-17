package com.sofka.microservicios.cuentas.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO que representa una solicitud para crear un movimiento bancario.
 * Contiene información sobre el ID de la cuenta, tipo de movimiento y valor.
 * 
 * 
 */


public class MovimientoRequest {
    @NotNull
    private Long cuentaId;
    @NotBlank
    private String tipoMovimiento;
    @NotNull
    private BigDecimal valor;

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
