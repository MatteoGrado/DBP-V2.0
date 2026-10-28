package de.grado.backofficeservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig
{
    @Bean
    public WebClient customerWebClient(@Value("${services.customer.url}") String customerUrl)
    {
        return WebClient.builder()
                .baseUrl(customerUrl)
                .build();
    }
}
