package com.sofka.microservicios.clientes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MsClientesPersonasApplication {

    private static final Logger log = LoggerFactory.getLogger(MsClientesPersonasApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MsClientesPersonasApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void mostrarSwaggerUrl() {
        log.info("Swagger UI: http://localhost:8081/swagger-ui/index.html");
    }
}
