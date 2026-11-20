package de.grado.backofficeservice.events;

import java.math.BigDecimal;

public record CreateTransactionEntry(
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
