package com.sofka.microservicios.cuentas.exception;

public class SaldoNoDisponibleException extends RuntimeException {
    public SaldoNoDisponibleException() {
        super("Saldo no disponible");
    }
}
