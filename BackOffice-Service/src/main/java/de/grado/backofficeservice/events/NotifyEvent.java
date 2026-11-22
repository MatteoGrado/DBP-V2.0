package de.grado.backofficeservice.events;

import java.util.UUID;

public record NotifyEvent(
        String firstName,
        String lastName,
        String email,
        String amount,
        String ibanTo,
        UUID advisorId
)
{
    public NotifyEvent()
    {
        this(null, null, null, null, null, null);
    }
}
