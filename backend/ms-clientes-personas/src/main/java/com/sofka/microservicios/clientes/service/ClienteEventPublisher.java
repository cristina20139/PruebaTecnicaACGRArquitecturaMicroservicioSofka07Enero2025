package com.sofka.microservicios.clientes.service;

import com.sofka.microservicios.clientes.domain.Cliente;

public interface ClienteEventPublisher {
    void publicarClienteCreado(Cliente cliente);
    void publicarClienteActualizado(Cliente cliente);
}
