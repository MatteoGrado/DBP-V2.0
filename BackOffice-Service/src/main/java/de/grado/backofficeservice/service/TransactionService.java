package de.grado.backofficeservice.service;

import de.grado.backofficeservice.dto.CreateTransactionDTO;
import de.grado.backofficeservice.events.CreateTransactionEntry;
import de.grado.backofficeservice.model.TempTransaction;
import de.grado.backofficeservice.repository.TempTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService
{
    private final TempTransactionRepository tempTransactionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<TempTransaction> getTempTransactions()
    {
        log.info("Getting List of temp transactions");
        return tempTransactionRepository.findAll();
    }

    public UUID createTransaction(CreateTransactionDTO createTransactionDTO)
    {
        log.info("Creating Transactions in Db");
        TempTransaction tempTransaction = new TempTransaction();
        UUID transactionId = UUID.randomUUID();
        tempTransaction.setTransactionId(transactionId);

        tempTransaction.setFromName(createTransactionDTO.getFromName());
        tempTransaction.setFromCountry(createTransactionDTO.getFromCountry());
        tempTransaction.setFromIban(createTransactionDTO.getFromIban());
        tempTransaction.setFromBic(createTransactionDTO.getFromBic());
        tempTransaction.setAmount(createTransactionDTO.getAmount());
        tempTransaction.setToName(createTransactionDTO.getToName());
        tempTransaction.setToCountry(createTransactionDTO.getToCountry());
        tempTransaction.setToIban(createTransactionDTO.getToIban());
        tempTransaction.setToBic(createTransactionDTO.getToBic());

        tempTransactionRepository.save(tempTransaction);
        sendCreatedTransactions(transactionId, createTransactionDTO);
        return transactionId;
    }

    public void sendCreatedTransactions(UUID transactionId, CreateTransactionDTO dto)
    {
        CreateTransactionEntry event = new CreateTransactionEntry(
                transactionId,
                dto.getFromName(),
                dto.getFromIban(),
                dto.getFromBic(),
                dto.getAmount(),
                dto.getFromCountry(),
                dto.getToCountry(),
                dto.getToName(),
                dto.getToIban(),
                dto.getToBic()
        );

        kafkaTemplate.send("create-transaction", transactionId.toString(), event);
    }
}
