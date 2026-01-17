package com.sofka.microservicios.cuentas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofka.microservicios.cuentas.dto.MovimientoRequest;
import com.sofka.microservicios.cuentas.dto.MovimientoResponse;
import com.sofka.microservicios.cuentas.service.MovimientoService;

/**
 * Controlador REST para la gestión de movimientos.
 * Proporciona endpoints para listar, crear y eliminar movimientos.
 * 
 * 
 */

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    /**
     * 
     * @param movimientoService
     */

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    /**
     * Lista todos los movimientos.
     * 
     * @return
     */

    @GetMapping
    public List<MovimientoResponse> listar() {
        return movimientoService.listar();
    }

    /**
     * Crea un nuevo movimiento.
     * 
     * @param request
     * @return
     */ 

    @PostMapping
    public ResponseEntity<MovimientoResponse> crear(@Validated @RequestBody MovimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.crear(request));
    }

    /**
     * Elimina un movimiento por su ID.
     * 
     * @param id
     * @return
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        movimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
