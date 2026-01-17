package com.sofka.microservicios.clientes.exception;

public class DuplicateIdentificacionException extends RuntimeException {
    public DuplicateIdentificacionException(String message) {
        super(message);
    }
}
