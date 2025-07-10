// Swagger/OpenAPI konfigürasyonu
package com.bahattintok.JWT_Authentication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Spring konfigürasyon sınıfı
public class OpenApiConfig {
    @Bean // OpenAPI bean'i oluşturur
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // JWT Bearer auth şeması ekleniyor
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                // API başlık, versiyon ve açıklama bilgisi
                .info(new Info()
                        .title("JWT Authentication API")
                        .version("1.0")
                        .description("JWT tabanlı kimlik doğrulama için API dokümantasyonu"));
    }
} 