package de.grado.customerservice;

import de.grado.customerservice.config.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class CustomerServiceApplicationTests
{

    @Test
    void contextLoads()
    {
    }

}
