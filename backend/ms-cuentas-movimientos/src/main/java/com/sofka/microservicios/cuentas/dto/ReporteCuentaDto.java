package com.sofka.microservicios.cuentas.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que representa un reporte detallado de una cuenta bancaria.
 * Contiene información sobre el cliente, número de cuenta, tipo, saldo inicial, estado y una lista de movimientos asociados.
 * 
 * 
 */

public class ReporteCuentaDto {
    private Long clienteId;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private List<ReporteMovimientoDto> movimientos = new ArrayList<>();

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

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

    public List<ReporteMovimientoDto> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<ReporteMovimientoDto> movimientos) {
        this.movimientos = movimientos;
    }
}
