package de.grado.customerservice.integration;

import de.grado.customerservice.model.Customer;
import de.grado.customerservice.repositorys.CustomerRepository;
import de.grado.customerservice.config.TestSecurityConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfiguration.class)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:customer-service-test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.cloud.consul.enabled=false",
        "spring.cloud.consul.discovery.enabled=false",
        "spring.kafka.bootstrap-servers=localhost:19092"
})
class CustomerControllerIntegrationTest
{
    private static final UUID ADVISOR_ID = UUID.randomUUID();
    private static final UUID OTHER_ADVISOR_ID = UUID.randomUUID();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void cleanDatabase()
    {
        customerRepository.deleteAll();
    }

    @Test
    void advisorOnlySeesAssignedCustomers() throws Exception
    {
        customerRepository.save(customer("Assigned", ADVISOR_ID));
        customerRepository.save(customer("Hidden", OTHER_ADVISOR_ID));

        mockMvc.perform(get("/api/v1/customer/getCustomerList")
                        .with(jwt().jwt(token -> token.subject(ADVISOR_ID.toString()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value("Assigned"));
    }

    @Test
    void advisorCannotFetchAnotherAdvisorsCustomer() throws Exception
    {
        Customer hidden = customerRepository.save(customer("Hidden", OTHER_ADVISOR_ID));

        mockMvc.perform(get("/api/v1/customer/getCustomer/{id}", hidden.getId())
                        .with(jwt().jwt(token -> token.subject(ADVISOR_ID.toString()))))
                .andExpect(status().isNotFound());
    }

    @Test
    void adminCanSeeAllCustomers() throws Exception
    {
        customerRepository.save(customer("Assigned", ADVISOR_ID));
        customerRepository.save(customer("Other", OTHER_ADVISOR_ID));

        mockMvc.perform(get("/api/v1/customer/getCustomerList")
                        .with(jwt().jwt(token -> token.subject(ADVISOR_ID.toString()))
                                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void normalUserIsAssignedToCreatedCustomer() throws Exception
    {
        mockMvc.perform(post("/api/v1/customer/createUser")
                        .with(jwt().jwt(token -> token.subject(ADVISOR_ID.toString())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"advisorId":"%s","firstName":"New","lastName":"Customer"}
                                """.formatted(OTHER_ADVISOR_ID)))
                .andExpect(status().isOk());

        Customer created = customerRepository.findAll().getFirst();
        org.junit.jupiter.api.Assertions.assertEquals(ADVISOR_ID, created.getAdvisorId());
    }

    private static Customer customer(String firstName, UUID advisorId)
    {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName("Customer");
        customer.setAdvisorId(advisorId);
        return customer;
    }

}
