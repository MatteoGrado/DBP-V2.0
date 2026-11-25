package de.grado.backofficeservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:backoffice-service-context;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.cloud.consul.enabled=false",
        "spring.cloud.consul.discovery.enabled=false",
        "spring.kafka.bootstrap-servers=localhost:19092",
        "spring.data.redis.repositories.enabled=false",
        "spring.data.redis.host=localhost",
        "spring.data.redis.port=16379",
        "keycloak.client-secret=test-client-secret"
})
class BackOfficeServiceApplicationTests
{

    @Test
    void contextLoads()
    {
    }

}
