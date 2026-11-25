package de.grado.backofficeservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateTransactionDTO
{
    private UUID customerId;
    private String fromName;
    private String fromIban;
    private String fromBic;
    private BigDecimal amount;
    private String fromCountry;
    private String toCountry;
    private String toName;
    private String toIban;
    private String toBic;
}
