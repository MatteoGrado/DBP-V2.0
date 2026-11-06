package de.grado.backofficeservice.service;

import de.grado.backofficeservice.component.MailBoxMapper;
import de.grado.backofficeservice.dto.CreateMailBoxEntryDTO;
import de.grado.backofficeservice.exceptions.MailBoxNotFoundException;
import de.grado.backofficeservice.model.Mailbox;
import de.grado.backofficeservice.repository.MailboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<Mailbox> listMailBoxEntries()
    {
        log.info("Listing Mailbox entries.");
        return mailboxRepository.findAll();
    }

    public Optional<Mailbox> getEntry(UUID id) throws MailBoxNotFoundException
    {
        log.info("Getting Mailbox entry with id: {}", id);
        Optional<Mailbox> mailbox = mailboxRepository.findById(id);
        if (mailbox.isEmpty()) {
            log.info("Mailbox entry with id: {} not found.", id);
            throw new MailBoxNotFoundException("No Entry with id: " + id + " found!");
        }
        return mailbox;
    }

    @Transactional
    public void deleteEntry(UUID id)
    {
        log.info("Deleting Mailbox entry with id: {}", id);
        mailboxRepository.deleteById(id);
    }
}
