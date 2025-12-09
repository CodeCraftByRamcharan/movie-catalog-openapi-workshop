package com.example.moviecatalog.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Movie Catalog API", version = "1.0", description = "API for managing movies"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server")
        },
        externalDocs = @ExternalDocumentation(description = "Movie API Docs", url = "https://example.com/docs"),
        tags = {
                @Tag(name = "Movie API", description = "Operations related to movies")
        }
        //,
        //security = @SecurityRequirement(name= "bearerAuth")

)
public class OpenApiConfig {}
