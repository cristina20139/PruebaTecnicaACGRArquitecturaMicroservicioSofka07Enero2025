package com.sofka.microservicios.cuentas.dto;

/**
 * DTO que representa un evento relacionado con un cliente.
 * Utilizado para comunicar cambios en el estado del cliente.
 * 
 * 
 */

public class ClienteEvent {
    private Long clienteId;
    private Boolean estado;
    private String tipo;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
