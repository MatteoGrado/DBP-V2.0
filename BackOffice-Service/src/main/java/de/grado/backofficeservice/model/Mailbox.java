package de.grado.backofficeservice.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "customer_mailbox")
@Data
public class Mailbox
{
    @Id
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;

    private String subject;
    private String body;

    @Nullable
    private String fileUrl;
}
