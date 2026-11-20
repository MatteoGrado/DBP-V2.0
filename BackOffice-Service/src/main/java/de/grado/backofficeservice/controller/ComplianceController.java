package de.grado.backofficeservice.controller;

import de.grado.backofficeservice.dto.CreateTransactionDTO;
import de.grado.backofficeservice.model.TempTransaction;
import de.grado.backofficeservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/compliance")
@RequiredArgsConstructor
@Slf4j
public class ComplianceController
{
    private final TransactionService transactionService;

    @Operation(summary = "Lists transactions", description = "Lists all Transactions made by customers")
    @GetMapping("/getTransactionList")
    public ResponseEntity<List<TempTransaction>> getTransactions()
    {
        log.info("Return List of all Transactions");
        return ResponseEntity.ok(transactionService.getTempTransactions());
    }

    @Operation(summary = "Writes the Transactions to DB", description = "Transactions get created in db")
    @PostMapping("/createTransaction")
    public ResponseEntity<UUID> createTransaction(@RequestBody CreateTransactionDTO createTransactionDTO)
    {
        log.info("Created Transaction");
        UUID transactionId = transactionService.createTransaction(createTransactionDTO);
        return ResponseEntity.ok(transactionId);
    }
}
