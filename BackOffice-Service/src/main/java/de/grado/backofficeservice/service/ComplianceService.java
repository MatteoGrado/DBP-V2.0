package de.grado.backofficeservice.service;

import de.grado.backofficeservice.repository.TempTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplianceService
{
    private final TempTransactionRepository tempTransactionRepository;
}
