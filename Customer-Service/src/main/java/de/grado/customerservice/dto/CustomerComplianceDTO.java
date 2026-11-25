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
    private Integer score;

    public CustomerComplianceDTO(String firstName, String lastName, String email, UUID advisorId, Integer score)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.advisorId = advisorId;
        this.score = score;
    }
}
