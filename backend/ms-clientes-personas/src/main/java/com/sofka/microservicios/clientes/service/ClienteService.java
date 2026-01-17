package com.sofka.microservicios.clientes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sofka.microservicios.clientes.domain.Cliente;
import com.sofka.microservicios.clientes.dto.ClienteRequest;
import com.sofka.microservicios.clientes.dto.ClienteResponse;
import com.sofka.microservicios.clientes.exception.DuplicateIdentificacionException;
import com.sofka.microservicios.clientes.exception.ResourceNotFoundException;
import com.sofka.microservicios.clientes.repository.ClienteRepository;

@Service
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;
    private final ClienteEventPublisher eventPublisher;

    public ClienteService(ClienteRepository clienteRepository, ClienteEventPublisher eventPublisher) {
        this.clienteRepository = clienteRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResponse obtener(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id " + id));
        return toResponse(cliente);
    }

    @Transactional
    public ClienteResponse crear(ClienteRequest request) {
        clienteRepository.findByIdentificacion(request.getIdentificacion()).ifPresent(existing -> {
            throw new DuplicateIdentificacionException("Ya existe un cliente con identificacion " + request.getIdentificacion());
        });

        Cliente nuevo = new Cliente();
        copy(request, nuevo);
        Cliente guardado = clienteRepository.save(nuevo);
        log.info("Cliente creado con id {}", guardado.getId());
        eventPublisher.publicarClienteCreado(guardado);
        return toResponse(guardado);
    }

    @Transactional
    public ClienteResponse actualizar(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id " + id));

        clienteRepository.findByIdentificacion(request.getIdentificacion()).ifPresent(otro -> {
            if (!otro.getId().equals(id)) {
                throw new DuplicateIdentificacionException("Ya existe un cliente con identificacion " + request.getIdentificacion());
            }
        });

        copy(request, cliente);
        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente actualizado con id {}", guardado.getId());
        eventPublisher.publicarClienteActualizado(guardado);
        return toResponse(guardado);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id " + id);
        }
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado con id {}", id);
    }

    private void copy(ClienteRequest request, Cliente cliente) {
        cliente.setNombre(request.getNombre());
        cliente.setGenero(request.getGenero());
        cliente.setEdad(request.getEdad());
        cliente.setIdentificacion(request.getIdentificacion());
        cliente.setDireccion(request.getDireccion());
        cliente.setTelefono(request.getTelefono());
        cliente.setContrasena(request.getContrasena());
        cliente.setEstado(request.getEstado());
    }

    private ClienteResponse toResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNombre(cliente.getNombre());
        response.setGenero(cliente.getGenero());
        response.setEdad(cliente.getEdad());
        response.setIdentificacion(cliente.getIdentificacion());
        response.setDireccion(cliente.getDireccion());
        response.setTelefono(cliente.getTelefono());
        response.setEstado(cliente.getEstado());
        return response;
    }
}
