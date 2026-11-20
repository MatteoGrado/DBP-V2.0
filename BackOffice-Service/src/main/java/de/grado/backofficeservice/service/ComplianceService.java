package de.grado.backofficeservice.service;

import de.grado.backofficeservice.dto.CreateTransactionDTO;
import de.grado.backofficeservice.model.TempTransaction;
import de.grado.backofficeservice.repository.TempTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplianceService
{
    private final TempTransactionRepository tempTransactionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<TempTransaction> getTempTransactions()
    {
        log.info("Getting List of temp transactions");
        return tempTransactionRepository.findAll();
    }

    public void createTransaction(CreateTransactionDTO createTransactionDTO)
    {
        log.info("Creating Transactions in Db");
        TempTransaction tempTransaction = new TempTransaction();

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
        //TODO: Add event for kafka
    }
}
