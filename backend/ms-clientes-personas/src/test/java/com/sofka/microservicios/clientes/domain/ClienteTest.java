package com.sofka.microservicios.clientes.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void shouldInheritPersonaData() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Jose Lema");
        cliente.setGenero("M");
        cliente.setEdad(30);
        cliente.setIdentificacion("123456");
        cliente.setDireccion("Otavalo sn y principal");
        cliente.setTelefono("098254785");
        cliente.setContrasena("1234");
        cliente.setEstado(true);

        assertEquals("Jose Lema", cliente.getNombre());
        assertEquals("123456", cliente.getIdentificacion());
        assertEquals("1234", cliente.getContrasena());
        assertTrue(cliente.getEstado());
    }
}
