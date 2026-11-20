package de.grado.backofficeservice.events;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTransactionEntry(
        UUID transactionId,
        String fromName,
        String fromIban,
        String fromBic,
        BigDecimal amount,
        String fromCountry,
        String toCountry,
        String toName,
        String toIban,
        String toBic
)
{
}
