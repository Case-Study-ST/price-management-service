package com.gsa.pricing.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pricingApi() {

        return new OpenAPI()

                .info(new Info()
                        .title("Pricing Service API")
                        .version("v1.0.0")
                        .description("""
                                Pricing service manages seat-based pricing per show.
                                
                                Features:
                                • Seat type pricing
                                • Dynamic pricing window
                                • Surge pricing ready
                                • Offer engine integration ready
                                
                                Used by Booking and Show services.
                                """)
                        .contact(new Contact()
                                .name("Platform Engineering")
                                .email("platform-support@gsa.com")
                                .url("https://gsa.com"))
                        .license(new License()
                                .name("Internal Platform License")
                                .url("https://gsa.com/license"))
                )

                .servers(List.of(
                        new Server()
                                .url("http://localhost:8085")
                                .description("Local Environment"),

                        new Server()
                                .url("https://api-dev.gsa.com/pricing")
                                .description("Development"),

                        new Server()
                                .url("https://api.gsa.com/pricing")
                                .description("Production")
                ))

                .externalDocs(new ExternalDocumentation()
                        .description("Pricing Service Architecture Docs")
                        .url("https://gsa.com/docs/pricing"));
    }
}
