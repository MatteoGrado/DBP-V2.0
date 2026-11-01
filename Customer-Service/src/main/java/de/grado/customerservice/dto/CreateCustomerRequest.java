package de.grado.customerservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateCustomerRequest
{
    private UUID advisorId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String city;
}
