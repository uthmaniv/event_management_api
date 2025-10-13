package com.uthmaniv.event_management_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Uthman",
                        email = "uthmanyahayababa@gmail.com",
                        url = "https://github.com/uthmaniv"
                ),
                title = "Event Management System API",
                description = "This API exposes endpoints to manage events and their participants",
                version = "1.0"
        ),
        servers = @Server(
                description = "Local Environment",
                url = "http://localhost:8080/"
        ),
        security = @SecurityRequirement(name = "basicAuth")
)
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class OpenApiConfig {
}
