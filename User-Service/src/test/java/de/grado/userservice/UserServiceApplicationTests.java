package de.grado.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@TestPropertySource(properties = {
        "keycloak.client-secret=test-client-secret",
        "spring.cloud.consul.enabled=false",
        "spring.cloud.consul.discovery.enabled=false"
})
class UserServiceApplicationTests
{

    @Test
    void contextLoads()
    {
    }

}
