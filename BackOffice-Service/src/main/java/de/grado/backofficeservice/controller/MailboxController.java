package de.grado.backofficeservice.controller;

import de.grado.backofficeservice.dto.CreateMailBoxEntryDTO;
import de.grado.backofficeservice.service.MailBoxService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mailbox")
@RequiredArgsConstructor
public class MailboxController
{
    private final MailBoxService mailBoxService;

    @Operation(summary = "Create new entry", description = "Creates an new entry from bank side.")
    @PostMapping("/createMessage")
    public void createMessage(@RequestBody CreateMailBoxEntryDTO mailBoxEntryDTO)
    {
        mailBoxService.createMessage(mailBoxEntryDTO);
    }
}
