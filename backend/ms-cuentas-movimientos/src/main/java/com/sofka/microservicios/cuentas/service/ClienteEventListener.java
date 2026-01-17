package com.sofka.microservicios.cuentas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sofka.microservicios.cuentas.dto.ClienteEvent;

@Component
public class ClienteEventListener {

    private static final Logger log = LoggerFactory.getLogger(ClienteEventListener.class);
    private final ClienteSnapshotService snapshotService;

    public ClienteEventListener(ClienteSnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @KafkaListener(topics = "clientes.eventos", groupId = "${spring.kafka.consumer.group-id:cuentas}")
    public void onClienteEvent(ClienteEvent event) {
        log.info("Recibido evento cliente {} estado {}", event.getClienteId(), event.getEstado());
        snapshotService.upsert(event.getClienteId(), event.getEstado());
    }
}
