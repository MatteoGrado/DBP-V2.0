package de.grado.backofficeservice.service;

import de.grado.backofficeservice.events.CreateTransactionEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;

@Service
@Slf4j
public class ComplianceService
{
    private static final BigDecimal HIGH_VALUE_LIMIT = BigDecimal.valueOf(10_000);

    public void checkTransaction(CreateTransactionEntry transaction)
    {
        log.info("Checking transaction {} for compliance", transaction.transactionId());

        String destinationIban = normalizeIban(transaction.toIban());
        boolean destinationIsAbroad = destinationIban != null && !destinationIban.startsWith("DE");

        if (destinationIsAbroad) {
            log.warn("Transaction {} has a foreign destination IBAN: {}",
                    transaction.transactionId(), transaction.toIban());
        }

        if (transaction.amount() != null && transaction.amount().compareTo(HIGH_VALUE_LIMIT) > 0) {
            log.warn("Transaction {} exceeds the high-value limit of {}. Follow-up action is pending.",
                    transaction.transactionId(), HIGH_VALUE_LIMIT);
            // TODO: Add method when acount is bigger than 10k
            //  Use Kafka}
        }

        private String normalizeIban (String iban)
        {
            if (iban == null || iban.isBlank()) {
                return null;
            }

            return iban.replaceAll("\\s+", "")
                    .toUpperCase(Locale.ROOT);
        }
    }
