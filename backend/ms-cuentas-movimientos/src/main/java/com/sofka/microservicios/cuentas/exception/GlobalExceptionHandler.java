package com.sofka.microservicios.cuentas.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para la aplicación de cuentas y movimientos.
 * Captura diversas excepciones y devuelve respuestas HTTP adecuadas con detalles del error.
 * 
 * 
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción ResourceNotFoundException.
     * 
     * @param ex
     * @return
     */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Maneja la excepción SaldoNoDisponibleException.
     * 
     * @param ex
     * @return
     */
    
    @ExceptionHandler(SaldoNoDisponibleException.class)
    public ResponseEntity<Map<String, Object>> handleSaldo(SaldoNoDisponibleException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Maneja la excepción MethodArgumentNotValidException.
     * 
     * @param ex
     * @return
     */
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String field = ((FieldError) err).getField();
            errors.put(field, err.getDefaultMessage());
        });
        body.put("errors", errors);
        return ResponseEntity.badRequest().body(body);
    }
    /**
     * Maneja excepciones genéricas.
     * 
     * @param ex
     * @return
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /**
     * Construye una respuesta HTTP con el estado y mensaje proporcionados.
     * 
     * @param status
     * @param message
     * @return
     */

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
