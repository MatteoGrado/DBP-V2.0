package de.grado.backofficeservice.component;

import de.grado.backofficeservice.dto.CreateMailBoxEntryDTO;
import de.grado.backofficeservice.model.Mailbox;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class MailBoxMapper
{
    public Mailbox toModel(CreateMailBoxEntryDTO dto)
    {
        Objects.requireNonNull(dto, "dto must not be null");

        Mailbox mailbox = new Mailbox();
        mailbox.setId(UUID.randomUUID());
        mailbox.setFirstName(dto.getFirstName());
        mailbox.setLastName(dto.getLastName());
        mailbox.setEmail(dto.getEmail());
        mailbox.setSubject(dto.getSubject());
        mailbox.setBody(dto.getBody());

        return mailbox;
    }

    public CreateMailBoxEntryDTO toDTO(Mailbox mailbox)
    {
        Objects.requireNonNull(mailbox, "mailbox must not be null");

        CreateMailBoxEntryDTO dto = new CreateMailBoxEntryDTO();
        dto.setFirstName(mailbox.getFirstName());
        dto.setLastName(mailbox.getLastName());
        dto.setEmail(mailbox.getEmail());
        dto.setSubject(mailbox.getSubject());
        dto.setBody(mailbox.getBody());

        return dto;
    }
}
