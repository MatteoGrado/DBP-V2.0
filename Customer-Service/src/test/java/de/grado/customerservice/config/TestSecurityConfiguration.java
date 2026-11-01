package de.grado.customerservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@TestConfiguration(proxyBeanMethods = false)
public class TestSecurityConfiguration
{
    @Bean
    JwtDecoder jwtDecoder()
    {
        return token -> {
            throw new UnsupportedOperationException("JWT decoder is not used with MockMvc jwt()");
        };
    }
}
