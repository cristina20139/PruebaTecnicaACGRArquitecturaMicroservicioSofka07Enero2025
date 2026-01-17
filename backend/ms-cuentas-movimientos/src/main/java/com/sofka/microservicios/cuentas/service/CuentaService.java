package com.sofka.microservicios.cuentas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sofka.microservicios.cuentas.domain.Cuenta;
import com.sofka.microservicios.cuentas.dto.CuentaRequest;
import com.sofka.microservicios.cuentas.dto.CuentaResponse;
import com.sofka.microservicios.cuentas.exception.ResourceNotFoundException;
import com.sofka.microservicios.cuentas.repository.CuentaRepository;

@Service
public class CuentaService {

    private static final Logger log = LoggerFactory.getLogger(CuentaService.class);

    private final CuentaRepository cuentaRepository;
    private final ClienteSnapshotService snapshotService;

    public CuentaService(CuentaRepository cuentaRepository, ClienteSnapshotService snapshotService) {
        this.cuentaRepository = cuentaRepository;
        this.snapshotService = snapshotService;
    }

    @Transactional(readOnly = true)
    public List<CuentaResponse> listar() {
        return cuentaRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CuentaResponse obtener(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id " + id));
        return toResponse(cuenta);
    }

    @Transactional
    public CuentaResponse crear(CuentaRequest request) {
        if (!snapshotService.clienteActivo(request.getClienteId())) {
            throw new ResourceNotFoundException("Cliente inactivo o no encontrado, no se puede crear cuenta");
        }
        Cuenta cuenta = new Cuenta();
        copy(request, cuenta);
        Cuenta guardada = cuentaRepository.save(cuenta);
        log.info("Cuenta creada numero {}", guardada.getNumeroCuenta());
        return toResponse(guardada);
    }

    @Transactional
    public CuentaResponse actualizar(Long id, CuentaRequest request) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id " + id));
        copy(request, cuenta);
        return toResponse(cuentaRepository.save(cuenta));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cuenta no encontrada con id " + id);
        }
        cuentaRepository.deleteById(id);
    }

    private void copy(CuentaRequest request, Cuenta cuenta) {
        cuenta.setNumeroCuenta(request.getNumeroCuenta());
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setSaldoInicial(request.getSaldoInicial());
        cuenta.setEstado(request.getEstado());
        cuenta.setClienteId(request.getClienteId());
    }

    private CuentaResponse toResponse(Cuenta cuenta) {
        CuentaResponse response = new CuentaResponse();
        response.setId(cuenta.getId());
        response.setNumeroCuenta(cuenta.getNumeroCuenta());
        response.setTipoCuenta(cuenta.getTipoCuenta());
        response.setSaldoInicial(cuenta.getSaldoInicial());
        response.setEstado(cuenta.getEstado());
        response.setClienteId(cuenta.getClienteId());
        return response;
    }
}
