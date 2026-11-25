package de.grado.backofficeservice.controller;

import de.grado.backofficeservice.dto.CustomerDTO;
import de.grado.backofficeservice.dto.StartProcessDTO;
import de.grado.backofficeservice.service.BackOfficeService;
import de.grado.backofficeservice.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final CreditService creditService;

    @Operation(summary = "Requests List of Customer", description = "Lists all ids of the Customers")
    @GetMapping("/customerList")
    public ResponseEntity<List<CustomerDTO>> getCustomerList()
    {
        log.info("Getting Customer list");
        return ResponseEntity.ok(backOfficeService.getCustomers());
    }

    @Operation(summary = "This route starts the process to get an credit", description = "This route gets only used for credit over 100k")
    @PostMapping("/createCreditProcess")
    public void createCreditProcess(StartProcessDTO startProcessDTO)
    {
        log.info("Creating credit process");
        creditService.createCreditProcess(startProcessDTO);
    }
}
