package com.f1rst.bookapi.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Books Service API",
                version = "1.0",
                description = "API for Search Books from OpenLibrary",
                contact = @Contact(name = "Ramon Silva", email = "ramonsilva12305@outlook.com")
        ))
public class SwaggerConfig {
    }
