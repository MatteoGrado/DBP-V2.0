package de.grado.customerservice.unit;

import de.grado.customerservice.dto.CreateCustomerRequest;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.repositorys.CustomerRepository;
import de.grado.customerservice.service.CustomerService;
import de.grado.customerservice.service.OcrService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest
{
    private static final UUID ADVISOR_ID = UUID.randomUUID();
    private static final UUID OTHER_ADVISOR_ID = UUID.randomUUID();

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OcrService ocrService;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void customerListIsFilteredForAdvisor()
    {
        List<Customer> customers = List.of(customer(ADVISOR_ID));
        when(customerRepository.findAllByAdvisorId(ADVISOR_ID)).thenReturn(customers);
        assertSame(customers, customerService.getCustomerList(ADVISOR_ID, false));
        verify(customerRepository).findAllByAdvisorId(ADVISOR_ID);
        verify(customerRepository, never()).findAll();
    }

    @Test
    void adminCanSeeAllCustomers()
    {
        List<Customer> customers = List.of(customer(ADVISOR_ID), customer(OTHER_ADVISOR_ID));
        when(customerRepository.findAll()).thenReturn(customers);
        assertSame(customers, customerService.getCustomerList(ADVISOR_ID, true));
        verify(customerRepository).findAll();
        verify(customerRepository, never()).findAllByAdvisorId(any());
    }

    @Test
    void advisorCannotFindAnotherAdvisorsCustomer()
    {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findByIdAndAdvisorId(customerId, ADVISOR_ID)).thenReturn(Optional.empty());
        assertTrue(customerService.findCustomer(customerId, ADVISOR_ID, false).isEmpty());
        verify(customerRepository).findByIdAndAdvisorId(customerId, ADVISOR_ID);
        verify(customerRepository, never()).findById(customerId);
    }

    @Test
    void createCustomerStoresAdvisorId()
    {
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        customerService.createCustomer(request(ADVISOR_ID));
        verify(customerRepository).save(argThat(customer -> ADVISOR_ID.equals(customer.getAdvisorId())
                && "Matteo".equals(customer.getFirstName())));
    }

    @Test
    void unauthorizedAdvisorCannotUpdateCustomer()
    {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findByIdAndAdvisorId(customerId, ADVISOR_ID)).thenReturn(Optional.empty());
        assertTrue(customerService.updateCustomer(customerId, request(ADVISOR_ID), ADVISOR_ID, false).isEmpty());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void authorizedAdvisorCanUpdateCustomerButCannotReassignIt()
    {
        UUID customerId = UUID.randomUUID();
        Customer customer = customer(ADVISOR_ID);
        when(customerRepository.findByIdAndAdvisorId(customerId, ADVISOR_ID)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Optional<Customer> result = customerService.updateCustomer(customerId, request(OTHER_ADVISOR_ID), ADVISOR_ID, false);
        assertTrue(result.isPresent());
        assertEquals(ADVISOR_ID, result.get().getAdvisorId());
    }

    @Test
    void adminCanReassignCustomer()
    {
        UUID customerId = UUID.randomUUID();
        Customer customer = customer(ADVISOR_ID);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        customerService.updateCustomer(customerId, request(OTHER_ADVISOR_ID), ADVISOR_ID, true);
        assertEquals(OTHER_ADVISOR_ID, customer.getAdvisorId());
    }

    @Test
    void advisorCannotDeleteAnotherAdvisorsCustomer()
    {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.existsByIdAndAdvisorId(customerId, ADVISOR_ID)).thenReturn(false);
        assertFalse(customerService.deleteCustomer(customerId, ADVISOR_ID, false));
        verify(customerRepository, never()).deleteById(any());
    }

    private static Customer customer(UUID advisorId)
    {
        Customer customer = new Customer();
        customer.setAdvisorId(advisorId);
        customer.setFirstName("Matteo");
        return customer;
    }

    private static CreateCustomerRequest request(UUID advisorId)
    {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setAdvisorId(advisorId);
        request.setFirstName("Matteo");
        request.setLastName("Grado");
        request.setDateOfBirth("2003-10-18");
        return request;
    }
}
