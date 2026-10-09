package de.grado.customerservice.controller;

import de.grado.customerservice.dto.request.CreateCustomerRequest;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController
{
    private final CustomerService customerService;

    @GetMapping("/getCustomerList")
    public ResponseEntity<List<Customer>> getCustomerList()
    {
        List<Customer> customers = customerService.getCustomerList();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createCustomer(@RequestBody CreateCustomerRequest request)
    {
        customerService.createCustomer(request);
        return ResponseEntity.ok("Customer created successfully");
    }

    public void authenticateCustomer()
    {
    }
}
