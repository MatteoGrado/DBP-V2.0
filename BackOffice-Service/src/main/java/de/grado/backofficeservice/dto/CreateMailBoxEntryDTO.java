package de.grado.backofficeservice.dto;

import lombok.Data;

@Data
public class CreateMailBoxEntryDTO
{
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
    private String body;
}
