package de.grado.customerservice.unit;

import de.grado.customerservice.dto.CreateCustomerRequest;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.repositorys.CustomerRepository;
import de.grado.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest
{
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void createCustomerTest()
    {
        CreateCustomerRequest request = new CreateCustomerRequest();

        request.setFirstName("Matteo");
        request.setLastName("Grado");
        request.setDateOfBirth("2003-10-18");
        request.setEmail("Matteo.Grado@gmail.com");
        request.setPhoneNumber("+49 1520 2942232");
        request.setAddress("Geißeichstr. 29");
        request.setZipCode("70197");
        request.setCity("Stuttgart");

        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        customerService.createCustomer(request);

        verify(customerRepository, times(1))
                .save(any(Customer.class));
    }

    @Test
    public void findCustomerTest()
    {
        Customer customer = new Customer();

        customer.setFirstName("Matteo");
        customer.setLastName("Grado");
        customer.setDateOfBirth("2003-10-18");
        customer.setAddress("Teststr. 25");
        customer.setZipCode("70197");
        customer.setCity("Stuttgart");
        customer.setPhoneNumber("+49 1520 2942232");

        when(customerRepository.findById(any()))
                .thenReturn(Optional.of(customer));

    }
}
