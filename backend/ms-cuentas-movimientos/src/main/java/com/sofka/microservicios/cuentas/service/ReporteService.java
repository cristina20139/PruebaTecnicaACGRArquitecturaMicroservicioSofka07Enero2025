package com.sofka.microservicios.cuentas.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sofka.microservicios.cuentas.domain.Cuenta;
import com.sofka.microservicios.cuentas.domain.Movimiento;
import com.sofka.microservicios.cuentas.dto.ReporteCuentaDto;
import com.sofka.microservicios.cuentas.dto.ReporteMovimientoDto;
import com.sofka.microservicios.cuentas.repository.CuentaRepository;
import com.sofka.microservicios.cuentas.repository.MovimientoRepository;

@Service
public class ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    public ReporteService(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Transactional(readOnly = true)
    public List<ReporteCuentaDto> generar(Long clienteId, LocalDate desde, LocalDate hasta) {
        LocalDateTime inicio = desde != null ? desde.atStartOfDay() : LocalDate.MIN.atStartOfDay();
        LocalDateTime fin = hasta != null ? hasta.atTime(LocalTime.MAX) : LocalDate.MAX.atTime(LocalTime.MAX);

        return cuentaRepository.findByClienteId(clienteId).stream()
                .map(cuenta -> buildReporte(cuenta, inicio, fin))
                .collect(Collectors.toList());
    }

    private ReporteCuentaDto buildReporte(Cuenta cuenta, LocalDateTime inicio, LocalDateTime fin) {
        List<Movimiento> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(cuenta.getId(), inicio, fin);
        ReporteCuentaDto dto = new ReporteCuentaDto();
        dto.setClienteId(cuenta.getClienteId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setTipoCuenta(cuenta.getTipoCuenta());
        dto.setSaldoInicial(cuenta.getSaldoInicial());
        dto.setEstado(cuenta.getEstado());
        dto.setMovimientos(movimientos.stream().map(this::mapMovimiento).collect(Collectors.toList()));
        return dto;
    }

    private ReporteMovimientoDto mapMovimiento(Movimiento movimiento) {
        ReporteMovimientoDto dto = new ReporteMovimientoDto();
        dto.setFecha(movimiento.getFecha());
        dto.setTipo(movimiento.getTipoMovimiento());
        dto.setValor(movimiento.getValor());
        dto.setSaldoDisponible(movimiento.getSaldo());
        return dto;
    }
}
