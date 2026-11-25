package de.grado.backofficeservice.listener;

import de.grado.backofficeservice.events.CreateTransactionEntry;
import de.grado.backofficeservice.service.ComplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionListener
{
    private final ComplianceService complianceService;

    @KafkaListener(
            topics = "create-transaction",
            groupId = "compliance-workers",
            concurrency = "5"
    )
    public void processTransaction(CreateTransactionEntry transaction)
    {
        complianceService.checkTransaction(transaction);
    }
}
