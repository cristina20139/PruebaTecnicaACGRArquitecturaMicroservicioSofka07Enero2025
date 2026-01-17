package com.sofka.microservicios.clientes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.sofka.microservicios.clientes.domain.Cliente;
import com.sofka.microservicios.clientes.dto.ClienteEvent;

@Component
public class LoggingClienteEventPublisher implements ClienteEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(LoggingClienteEventPublisher.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public LoggingClienteEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publicarClienteCreado(Cliente cliente) {
        ClienteEvent event = new ClienteEvent(cliente.getId(), cliente.getEstado(), "ClienteCreado");
        kafkaTemplate.send("clientes.eventos", String.valueOf(cliente.getId()), event);
        log.info("Evento ClienteCreado enviado a Kafka: {}", event.getClienteId());
    }

    @Override
    public void publicarClienteActualizado(Cliente cliente) {
        ClienteEvent event = new ClienteEvent(cliente.getId(), cliente.getEstado(), "ClienteActualizado");
        kafkaTemplate.send("clientes.eventos", String.valueOf(cliente.getId()), event);
        log.info("Evento ClienteActualizado enviado a Kafka: {}", event.getClienteId());
    }
}
