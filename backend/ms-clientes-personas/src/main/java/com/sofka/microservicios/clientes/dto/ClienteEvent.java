package com.sofka.microservicios.clientes.dto;

public class ClienteEvent {
    private Long clienteId;
    private Boolean estado;
    private String tipo;

    public ClienteEvent() {
    }

    public ClienteEvent(Long clienteId, Boolean estado, String tipo) {
        this.clienteId = clienteId;
        this.estado = estado;
        this.tipo = tipo;
    }

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
