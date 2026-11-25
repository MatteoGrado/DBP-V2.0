package de.grado.backofficeservice.service;

import de.grado.backofficeservice.events.CreateTransactionEntry;
import de.grado.backofficeservice.events.NotifyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplianceService
{
    private static final BigDecimal HIGH_VALUE_LIMIT = BigDecimal.valueOf(10_000);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final WebClient webClient;

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

            NotifyEvent customer = getCustomer(transaction.customerId());

            NotifyEvent event = new NotifyEvent(
                    customer.firstName(),
                    customer.lastName(),
                    customer.email(),
                    customer.advisorId(),
                    customer.score(),
                    transaction.amount(),
                    transaction.toIban()
            );

            log.info("Bank and Customer get a notification");
            kafkaTemplate.send("notify-bank-and-customer", event);
        }

    }

    private String normalizeIban(String iban)
    {
        if (iban == null || iban.isBlank()) {
            return null;
        }

        return iban.replaceAll("\\s+", "")
                .toUpperCase(Locale.ROOT);
    }

    public NotifyEvent getCustomer(UUID customerId)
    {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/customer/getCustomer/notify/{id}")
                        .build(customerId))
                .retrieve()
                .bodyToMono(NotifyEvent.class)
                .block();
    }
}
