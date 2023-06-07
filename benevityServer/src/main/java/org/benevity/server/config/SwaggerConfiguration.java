package org.benevity.server.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Posts service API")
                                            .description("Provides the post API")
                                            .version("v.1.0.0")
                                            .license(new License().name("Apache 2.0")))
                            .externalDocs(new ExternalDocumentation().description("Jorge Bravo Córdoba").url("https://www.linkedin.com/in/jorgebc/"));
    }
}
