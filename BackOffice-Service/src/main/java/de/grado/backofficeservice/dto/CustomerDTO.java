package de.grado.backofficeservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerDTO
{
    private UUID id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String city;
}
