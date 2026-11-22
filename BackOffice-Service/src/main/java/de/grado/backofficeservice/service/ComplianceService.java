package de.grado.backofficeservice.service;

import de.grado.backofficeservice.events.CreateTransactionEntry;
import de.grado.backofficeservice.events.NotifyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
public class ComplianceService
{
    private static final BigDecimal HIGH_VALUE_LIMIT = BigDecimal.valueOf(10_000);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final WebClient webClient;

    public ComplianceService(KafkaTemplate<String, Object> kafkaTemplate, WebClient webClient)
    {
        this.kafkaTemplate = kafkaTemplate;
        this.webClient = webClient;
    }

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
            NotifyEvent event = new NotifyEvent(
                    getCustomer(transaction.customerId()),
                    transaction.amount(),
                    transaction.toIban()
            );

            log.info("Bank and Customer get a notification");
            kafkaTemplate.send("notify-bank-and-customer", event);
            // TODO: Add method when acount is bigger than 10k
            //  Use Kafka}
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
        NotifyEvent event = webClient.get()
                .uri("")
                .retrieve()
                .bodyToMono() //TODO: Get only required Information
                .block();
        //TODO: Brak the data off the request and put it into the kafka template send message
    }
}
