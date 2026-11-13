package de.grado.backofficeservice.service;

import de.grado.backofficeservice.model.TempTransaction;
import de.grado.backofficeservice.repository.TempTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplianceService
{
    private final TempTransactionRepository tempTransactionRepository;

    public List<TempTransaction> getTempTransactions()
    {
        log.info("Getting List of temp transactions");
        return tempTransactionRepository.findAll();
    }
}
