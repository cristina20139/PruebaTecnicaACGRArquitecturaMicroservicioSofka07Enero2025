package com.sofka.microservicios.cuentas.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un snapshot del estado del cliente.
 * Utilizada para almacenar información relevante del cliente en un momento dado.
 * 
 * 
 */

@Entity
@Table(name = "cliente_snapshot")
public class ClienteSnapshot {
    @Id
    private Long id;
    private Boolean estado;

    public ClienteSnapshot() {
    }

    public ClienteSnapshot(Long id, Boolean estado) {
        this.id = id;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
