package de.grado.backofficeservice.service;

import de.grado.backofficeservice.component.MailBoxMapper;
import de.grado.backofficeservice.dto.CreateMailBoxEntryDTO;
import de.grado.backofficeservice.repository.MailboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailBoxService
{
    private final MailboxRepository mailboxRepository;
    private final MailBoxMapper mailBoxMapper;

    @Transactional
    public void createMessage(CreateMailBoxEntryDTO createMailBoxEntryDTO)
    {
        log.info("Created MailBox Entry.");
        mailboxRepository.save(mailBoxMapper.toModel(createMailBoxEntryDTO));
    }
}
