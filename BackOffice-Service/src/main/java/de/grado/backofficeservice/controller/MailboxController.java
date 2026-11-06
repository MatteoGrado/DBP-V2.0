package de.grado.backofficeservice.controller;

import de.grado.backofficeservice.dto.CreateMailBoxEntryDTO;
import de.grado.backofficeservice.exceptions.MailBoxNotFoundException;
import de.grado.backofficeservice.model.Mailbox;
import de.grado.backofficeservice.service.MailBoxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/mailbox")
@RequiredArgsConstructor
@Slf4j
public class MailboxController
{
    private final MailBoxService mailBoxService;

    @Operation(summary = "Create new entry", description = "Creates an new entry from bank side.")
    @PostMapping("/createMessage")
    public ResponseEntity<String> createMessage(@RequestBody CreateMailBoxEntryDTO mailBoxEntryDTO)
    {
        mailBoxService.createMessage(mailBoxEntryDTO);
        return ResponseEntity.ok("Successfully created new entry");
    }

    @Operation(summary = "Gets a List of all entries", description = "Returns a list of mailbox entries")
    @GetMapping("/getMailBoxEntryList")
    public ResponseEntity<Map<String, List<Mailbox>>> listMailBoxEntries()
    {
        log.info("Returned mailbox entries list");
        return ResponseEntity
                .ok(Map.of("List of all Mailbox entries", mailBoxService.listMailBoxEntries()));
    }

    @Operation(summary = "Finds an entrie by id", description = "Requires an UUID for searching")
    @GetMapping("/getEntry/{id}")
    public ResponseEntity<Map<String, Object>> getEntryById(@PathVariable UUID id) throws MailBoxNotFoundException
    {
        log.info("Fetching Mailbox entry.");
        Optional<Mailbox> mailbox = mailBoxService.getEntry(id);

        return ResponseEntity.ok(Map.of("Mailbox", mailbox));
    }

    @Operation(summary = "Delete Mailbox Entry", description = "Deletes by UUID id an Entry from DB.")
    @DeleteMapping("/deleteEntry/{id}")
    public ResponseEntity<String> delelteMailboxEntry(@PathVariable UUID id)
    {
        log.info("Deleting Mailbox entry.");
        mailBoxService.deleteEntry(id);
        return ResponseEntity.ok("Successfully deleted Mailbox entry.");
    }
}
