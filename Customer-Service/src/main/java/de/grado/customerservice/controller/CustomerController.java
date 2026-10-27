package de.grado.customerservice.controller;

import de.grado.customerservice.dto.CreateCustomerRequest;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<String> getCustomer(@PathVariable UUID id)
    {
        customerService.findCustomer(id);
        return ResponseEntity.ok("Found customer with id " + id);
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createCustomer(@RequestBody CreateCustomerRequest request)
    {
        customerService.createCustomer(request);
        return ResponseEntity.ok("Customer created successfully");
    }

    @PostMapping("/authenticateCustomer")
    public ResponseEntity<String> authenticateCustomer(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException
    {
        Optional<Customer> authenticatedCustomer = customerService.authenticateCustomer(file);
        return authenticatedCustomer.map(customer -> ResponseEntity.ok("Customer authenticated with id "
                + customer.getId())).orElseGet(() -> ResponseEntity.status(401).body("Customer authentication failed"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody CreateCustomerRequest request)
    {
        Customer customer = customerService.updateCustomer(id, request);
        return ResponseEntity.ok().body(customer);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable UUID id)
    {
        boolean deleted = customerService.deleteCustomer(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Customer deleted successfully");
    }
}
