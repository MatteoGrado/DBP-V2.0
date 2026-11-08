package de.grado.backofficeservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "temp_transaction")
@Data
public class TempTransaction
{
    @Id
    private UUID transactionId;

    @Column(name = "from_name")
    private String fromName;
    @Column(name = "from_iban")
    private String fromIban;
    @Column(name = "from_bic")
    private String fromBic;
    private BigDecimal amount;
    @Column(name = "from_country")
    private String fromCountry;
    @Column(name = "to_country")
    private String toCountry;
    @Column(name = "to_name")
    private String toName;
    @Column(name = "to_iban")
    private String toIban;
    @Column(name = "to_bic")
    private String toBic;
}
