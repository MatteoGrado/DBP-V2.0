package de.grado.customerservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerComplianceDTO
{
    private String firstName;
    private String lastName;
    private String email;
    private UUID advisorId;

    public CustomerComplianceDTO(String firstName, String lastName, String email, UUID advisorId)
    {
        
    }
}
