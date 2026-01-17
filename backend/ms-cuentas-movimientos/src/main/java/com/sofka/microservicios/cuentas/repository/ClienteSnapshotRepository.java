package com.sofka.microservicios.cuentas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sofka.microservicios.cuentas.domain.ClienteSnapshot;

@Repository
public interface ClienteSnapshotRepository extends JpaRepository<ClienteSnapshot, Long> {
    Optional<ClienteSnapshot> findById(Long id);
}
