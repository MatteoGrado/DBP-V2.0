package de.grado.backofficeservice.controller;

import de.grado.backofficeservice.service.ComplianceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
@Slf4j
public class ComplianceController
{
    private final ComplianceService complianceService;

    @Operation(summary = "Lists transactions", description = "Lists all Transactions made by customers")
    @GetMapping("/getTransactionList")
    public void getTransactions()
    {
    }
}
