package de.grado.backofficeservice.service;

import de.grado.backofficeservice.dto.CustomerDTO;
import de.grado.backofficeservice.dto.StartProcessDTO;
import de.grado.backofficeservice.model.CreditProcess;
import de.grado.backofficeservice.repository.CreditProcessRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditService
{
    private final CreditProcessRepository creditProcessRepository;
    private final WebClient webClient;


    //TODO: When a specific file got load up parse it threw a method ta upload via name
    //TODO: For Download the same I get the name pare it threw to download and done
    @Transactional
    public void createCreditProcess(StartProcessDTO startProcessDTO)
    {
        CreditProcess creditProcess = new CreditProcess();

        creditProcessRepository.save(creditProcess);
    }

    public CustomerDTO getCustomer(String accountNumber)
    {
        //TODO: Webclient Request with first and lastname to get Customer
    }

    public void uploadFile()
    {
    }

    public void downloadFile()
    {
    }
}
