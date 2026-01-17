package com.sofka.microservicios.cuentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MsCuentasMovimientosApplication {

	private static final Logger log = LoggerFactory.getLogger(MsCuentasMovimientosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MsCuentasMovimientosApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void mostrarSwaggerUrl() {
		log.info("Swagger UI: http://localhost:8082/swagger-ui/index.html");
	}
}
