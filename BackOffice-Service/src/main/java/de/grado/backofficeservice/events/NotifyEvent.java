package de.grado.backofficeservice.events;

import java.util.UUID;
import java.math.BigDecimal;

public record NotifyEvent(
        String firstName,
        String lastName,
        String email,
        UUID advisorId,
        Integer score,
        BigDecimal amount,
        String ibanTo
)
{
    public NotifyEvent()
    {
        this(null, null, null, null, null, null, null);
    }
}
