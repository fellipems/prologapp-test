package com.prologapp.test.interview.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Prolog Interview API",
                version = "1.0",
                description = "Documentação da API do teste técnico Prolog - Gerenciamento de Veículos e Pneus"
        )
)
@Configuration
public class OpenApiConfig {
}
