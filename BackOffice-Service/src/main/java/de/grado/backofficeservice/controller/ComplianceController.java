package de.grado.backofficeservice.controller;

import de.grado.backofficeservice.dto.CreateTransactionDTO;
import de.grado.backofficeservice.model.TempTransaction;
import de.grado.backofficeservice.service.ComplianceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
@Slf4j
public class ComplianceController
{
    private final ComplianceService complianceService;

    @Operation(summary = "Lists transactions", description = "Lists all Transactions made by customers")
    @GetMapping("/getTransactionList")
    public ResponseEntity<List<TempTransaction>> getTransactions()
    {
        log.info("Return List of all Transactions");
        return ResponseEntity.ok(complianceService.getTempTransactions());
    }

    @Operation(summary = "Writes the Transactions to DB", description = "Transactions get created in db")
    @PostMapping("/createTransaction")
    public ResponseEntity<String> createTransaction(@RequestBody CreateTransactionDTO createTransactionDTO)
    {
        log.info("Created Transaction");
        complianceService.createTransaction(createTransactionDTO);
        return ResponseEntity.ok("Transaction successfully created");
    }
}
