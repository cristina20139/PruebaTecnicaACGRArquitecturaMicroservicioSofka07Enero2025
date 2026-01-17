package com.sofka.microservicios.cuentas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofka.microservicios.cuentas.dto.CuentaRequest;
import com.sofka.microservicios.cuentas.dto.CuentaResponse;
import com.sofka.microservicios.cuentas.service.CuentaService;


/**
 * Controlador REST para la gestión de cuentas.
 * Proporciona endpoints para crear, leer, actualizar y eliminar cuentas.
 * 
 * 
 */

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    /**
     * Obtiene todas las cuentas.
     * @return Lista de cuentas.
     */


    @GetMapping
    public List<CuentaResponse> listar() {
        return cuentaService.listar();
    }

    /**
     * Obtiene una cuenta por su ID.
     * @param id ID de la cuenta.
     * @return Detalles de la cuenta.
     */

    @GetMapping("/{id}")
    public CuentaResponse obtener(@PathVariable Long id) {
        return cuentaService.obtener(id);
    }

    /**
     * Crea una nueva cuenta.
     * @param request Datos de la cuenta a crear.
     * @return Detalles de la cuenta creada.
     */

    @PostMapping
    public ResponseEntity<CuentaResponse> crear(@Validated @RequestBody CuentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.crear(request));
    }

    /**
     * Actualiza una cuenta existente.
     * @param id ID de la cuenta a actualizar.
     * @param request Datos actualizados de la cuenta.
     * @return Detalles de la cuenta actualizada.
     */

    @PutMapping("/{id}")
    public CuentaResponse actualizar(@PathVariable Long id, @Validated @RequestBody CuentaRequest request) {
        return cuentaService.actualizar(id, request);
    }

    /**
     * Elimina una cuenta por su ID.
     * @param id ID de la cuenta a eliminar.
     * @return Respuesta sin contenido.
     */
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
