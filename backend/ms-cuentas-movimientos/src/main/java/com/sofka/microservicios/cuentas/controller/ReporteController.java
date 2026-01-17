package com.sofka.microservicios.cuentas.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sofka.microservicios.cuentas.dto.ReporteCuentaDto;
import com.sofka.microservicios.cuentas.service.ReporteService;

/**
 * Controlador REST para la generación de reportes de cuentas y movimientos.
 * Proporciona un endpoint para generar reportes basados en el ID del cliente
 * y un rango de fechas opcional.
 * 
 * 
 */

@RestController
public class ReporteController {

    private final ReporteService reporteService;

    /**
     * 
     * @param reporteService
     */

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    /**
     * Genera un reporte de cuentas y movimientos para un cliente específico
     * dentro de un rango de fechas opcional.
     * 
     * @param clienteId ID del cliente.
     * @param rango     Rango de fechas en formato "yyyy-MM-dd,yyyy-MM-dd".
     * @return Lista de reportes de cuentas.
     */
    
    @GetMapping("/api/reportes")
    public List<ReporteCuentaDto> generar(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam(name = "fecha", required = false) String rango) {

        LocalDate desde = null;
        LocalDate hasta = null;
        if (rango != null && !rango.isBlank()) {
            String[] partes = rango.split(",");
            if (partes.length == 2) {
                desde = LocalDate.parse(partes[0]);
                hasta = LocalDate.parse(partes[1]);
            }
        }

        return reporteService.generar(clienteId, desde, hasta);
    }
}
