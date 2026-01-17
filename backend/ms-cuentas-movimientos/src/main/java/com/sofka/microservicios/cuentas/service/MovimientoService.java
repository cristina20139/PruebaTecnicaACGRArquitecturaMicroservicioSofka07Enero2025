package com.sofka.microservicios.cuentas.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sofka.microservicios.cuentas.domain.Cuenta;
import com.sofka.microservicios.cuentas.domain.Movimiento;
import com.sofka.microservicios.cuentas.dto.MovimientoRequest;
import com.sofka.microservicios.cuentas.dto.MovimientoResponse;
import com.sofka.microservicios.cuentas.exception.ResourceNotFoundException;
import com.sofka.microservicios.cuentas.exception.SaldoNoDisponibleException;
import com.sofka.microservicios.cuentas.repository.CuentaRepository;
import com.sofka.microservicios.cuentas.repository.MovimientoRepository;

@Service
public class MovimientoService {

    private static final Logger log = LoggerFactory.getLogger(MovimientoService.class);

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteSnapshotService snapshotService;

    public MovimientoService(CuentaRepository cuentaRepository,
                             MovimientoRepository movimientoRepository,
                             ClienteSnapshotService snapshotService) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
        this.snapshotService = snapshotService;
    }

    @Transactional(readOnly = true)
    public List<MovimientoResponse> listar() {
        return movimientoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public MovimientoResponse crear(MovimientoRequest request) {
        Cuenta cuenta = cuentaRepository.findById(request.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id " + request.getCuentaId()));

        if (Boolean.FALSE.equals(cuenta.getEstado()) || !snapshotService.clienteActivo(cuenta.getClienteId())) {
            throw new ResourceNotFoundException("Cuenta o cliente inactivo, no se puede registrar el movimiento");
        }

        BigDecimal saldoActual = cuenta.getSaldoInicial();
        BigDecimal nuevoSaldo = saldoActual.add(request.getValor());
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoNoDisponibleException();
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(request.getTipoMovimiento());
        movimiento.setValor(request.getValor());
        movimiento.setSaldo(nuevoSaldo);

        cuenta.setSaldoInicial(nuevoSaldo);

        Movimiento guardado = movimientoRepository.save(movimiento);
        cuentaRepository.save(cuenta);
        log.info("Movimiento registrado en cuenta {} con saldo {}", cuenta.getNumeroCuenta(), nuevoSaldo);
        return toResponse(guardado);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Movimiento no encontrado con id " + id);
        }
        movimientoRepository.deleteById(id);
    }

    private MovimientoResponse toResponse(Movimiento movimiento) {
        MovimientoResponse dto = new MovimientoResponse();
        dto.setId(movimiento.getId());
        dto.setFecha(movimiento.getFecha());
        dto.setTipoMovimiento(movimiento.getTipoMovimiento());
        dto.setValor(movimiento.getValor());
        dto.setSaldo(movimiento.getSaldo());
        dto.setCuentaId(movimiento.getCuenta().getId());
        return dto;
    }
}
