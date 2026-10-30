package de.grado.backofficeservice.controller;

import de.grado.backofficeservice.dto.CustomerDTO;
import de.grado.backofficeservice.service.BackOfficeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/backoffice")
@RequiredArgsConstructor
@Slf4j
public class BackOfficeDashboardController
{
    private final BackOfficeService backOfficeService;

    @Operation(summary = "Requests List of Customer", description = "Lists all ids of the Customers")
    @GetMapping("/customerList")
    public ResponseEntity<List<CustomerDTO>> getCustomerList()
    {
        log.info("Getting Customer list");
        return ResponseEntity.ok(backOfficeService.getCustomers());
    }
}
