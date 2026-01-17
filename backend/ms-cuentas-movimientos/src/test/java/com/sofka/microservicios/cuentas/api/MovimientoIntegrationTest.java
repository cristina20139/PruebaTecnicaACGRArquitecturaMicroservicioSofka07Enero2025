package com.sofka.microservicios.cuentas.api;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
        "spring.microservicios-datasource.url=jdbc:h2:mem:movimientos;DB_CLOSE_DELAY=-1",
        "spring.microservicios-datasource.username=sa",
        "spring.microservicios-datasource.password=",
        "spring.microservicios-datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class MovimientoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void debeRegistrarMovimientoYValidarSaldoNoDisponible() throws Exception {
        // Crear cuenta
        String cuentaJson = """
                {
                  "numeroCuenta": "478758",
                  "tipoCuenta": "Ahorro",
                  "saldoInicial": 2000,
                  "estado": true,
                  "clienteId": 1
                }
                """;

        mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cuentaJson))
                .andExpect(status().isCreated());

        // Registrar retiro de 575
        String movimientoJson = """
                {
                  "cuentaId": 1,
                  "tipoMovimiento": "Retiro",
                  "valor": -575
                }
                """;

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movimientoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saldo", is(1425)));

        // Intentar retiro que genera saldo no disponible
        String movimientoSobregiro = """
                {
                  "cuentaId": 1,
                  "tipoMovimiento": "Retiro",
                  "valor": -2000
                }
                """;

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(movimientoSobregiro))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Saldo no disponible")));

        // Validar que el reporte filtra por cliente
        mockMvc.perform(get("/api/reportes")
                        .param("clienteId", "1")
                        .param("fecha", "2022-01-01,2030-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCuenta", is("478758")))
                .andExpect(jsonPath("$[0].movimientos[0].saldoDisponible", is(1425)));
    }
}
