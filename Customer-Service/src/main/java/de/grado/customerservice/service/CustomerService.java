package de.grado.customerservice.service;

import de.grado.customerservice.dto.request.CreateCustomerRequest;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.repositorys.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService
{
    private final CustomerRepository customerRepository;

    public List<Customer> getCustomerList()
    {
        log.info("Getting customer list");
        return customerRepository.findAll();
    }

    @Transactional
    public void createCustomer(CreateCustomerRequest request)
    {
        Customer customer = new Customer();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setZipCode(request.getZipCode());
        customer.setCity(request.getCity());
        customer.setDateOfBirth(request.getDateOfBirth());

        customerRepository.save(customer);
        log.info("Created customer with id {}", customer.getId());
    }
}
