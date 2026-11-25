package de.grado.backofficeservice.service;

import de.grado.backofficeservice.dto.StartProcessDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditService
{
    @Transactional
    public void createCreditProcess(StartProcessDTO startProcessDTO)
    {
        //TODO: When a specific file got load up parse it threw a method ta upload via name
        //TODO: For Download the same I get the name pare it threw to download and done
        //TODO: Webclient Request with first and lastname to get Customer
    }

    public void uploadFile()
    {
    }

    public void downloadFile()
    {
    }
}
