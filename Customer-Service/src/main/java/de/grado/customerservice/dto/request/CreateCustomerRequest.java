package de.grado.customerservice.dto.request;

import lombok.Data;

@Data
public class CreateCustomerRequest
{
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String city;
}
