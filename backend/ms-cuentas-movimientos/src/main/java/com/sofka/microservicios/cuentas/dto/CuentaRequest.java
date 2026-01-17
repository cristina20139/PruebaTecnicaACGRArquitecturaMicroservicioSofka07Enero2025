package com.sofka.microservicios.cuentas.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO que representa una solicitud para crear o actualizar una cuenta bancaria.
 * Contiene información sobre el número de cuenta, tipo, saldo inicial, estado y el ID del cliente asociado.
 * 
 * 
 */

public class CuentaRequest {
    @NotBlank
    private String numeroCuenta;
    @NotBlank
    private String tipoCuenta;
    @NotNull
    @Min(0)
    private BigDecimal saldoInicial;
    @NotNull
    private Boolean estado;
    @NotNull
    private Long clienteId;

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
