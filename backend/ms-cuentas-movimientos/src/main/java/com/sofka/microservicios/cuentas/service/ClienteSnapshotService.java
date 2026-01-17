package com.sofka.microservicios.cuentas.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sofka.microservicios.cuentas.domain.ClienteSnapshot;
import com.sofka.microservicios.cuentas.repository.ClienteSnapshotRepository;

@Service
public class ClienteSnapshotService {

    private final ClienteSnapshotRepository repository;

    public ClienteSnapshotService(ClienteSnapshotRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void upsert(Long id, Boolean estado) {
        ClienteSnapshot snapshot = repository.findById(id).orElse(new ClienteSnapshot(id, estado));
        snapshot.setEstado(estado);
        repository.save(snapshot);
    }

    @Transactional(readOnly = true)
    public boolean clienteActivo(Long id) {
        Optional<ClienteSnapshot> snap = repository.findById(id);
        return snap.map(ClienteSnapshot::getEstado).orElse(false);
    }
}
