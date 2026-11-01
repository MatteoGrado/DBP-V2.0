package de.grado.backofficeservice.repository;

import de.grado.backofficeservice.model.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, UUID>
{
}
