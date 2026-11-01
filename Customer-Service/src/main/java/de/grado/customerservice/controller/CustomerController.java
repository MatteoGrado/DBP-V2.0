package de.grado.customerservice.controller;

import de.grado.customerservice.dto.CreateCustomerRequest;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

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
    public ResponseEntity<List<Customer>> getCustomerList(Authentication authentication)
    {
        List<Customer> customers = customerService.getCustomerList(currentUserId(authentication), isAdmin(authentication));
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/getCustomer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable UUID id, Authentication authentication)
    {
        return customerService.findCustomer(id, currentUserId(authentication), isAdmin(authentication))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createCustomer(@RequestBody CreateCustomerRequest request,
                                                  Authentication authentication)
    {
        UUID authenticatedUserId = currentUserId(authentication);
        if (!isAdmin(authentication) || request.getAdvisorId() == null) {
            request.setAdvisorId(authenticatedUserId);
        }
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
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody CreateCustomerRequest request,
                                                    Authentication authentication)
    {
        return customerService.updateCustomer(id, request, currentUserId(authentication), isAdmin(authentication))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable UUID id, Authentication authentication)
    {
        boolean deleted = customerService.deleteCustomer(id, currentUserId(authentication), isAdmin(authentication));

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Customer deleted successfully");
    }

    private UUID currentUserId(Authentication authentication)
    {
        try {
            return UUID.fromString(authentication.getName());
        } catch (IllegalArgumentException exception) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "The authenticated user id must be a UUID", exception);
        }
    }

    private boolean isAdmin(Authentication authentication)
    {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")
                        || authority.getAuthority().equals("ADMIN"));
    }
}
