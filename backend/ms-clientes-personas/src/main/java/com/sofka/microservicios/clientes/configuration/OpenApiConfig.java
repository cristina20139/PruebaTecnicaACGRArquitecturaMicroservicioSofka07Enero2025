package com.sofka.microservicios.clientes.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI msClientesPersonasOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("MS Clientes Personas API")
                        .description("Documentacion de la API para gestion de clientes y personas.")
                        .version("v1.0.0")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio del proyecto")
                        .url("https://example.com/ms-clientes-personas"));
    }
}
