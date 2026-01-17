package com.sofka.microservicios.cuentas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sofka.microservicios.cuentas.domain.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDateTime desde, LocalDateTime hasta);
    List<Movimiento> findByCuentaId(Long cuentaId);
}
