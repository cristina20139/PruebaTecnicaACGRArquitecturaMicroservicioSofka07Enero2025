package com.sofka.microservicios.cuentas.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "MS Cuentas Movimientos API",
                version = "v1.0.0",
                description = "Documentacion de la API para gestion de cuentas y movimientos.",
                contact = @Contact(name = "Equipo Sofka", email = "auragarzonr@gmail.com")
        )
)
public class OpenApiConfig {

    @Bean
    public OpenAPI msCuentasMovimientosOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("MS Cuentas Movimientos API")
                        .description("Documentacion de la API para gestion de cuentas y movimientos.")
                        .version("v1.0.0")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio del proyecto")
                        .url("https://example.com/ms-cuentas-movimientos"));
    }
}
